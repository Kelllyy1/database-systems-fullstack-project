import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';
import LogoutButton from '../components/LogoutButton';
import RequestRSOForm from '../components/RequestRSOForm';
import StudentEvents from '../components/StudentEvents';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import 'bootstrap/dist/css/bootstrap.min.css';

const StudentDashboard = () => {
  const [user, setUser] = useState(null);
  const [rsos, setRsos] = useState([]);
  const [joinedRsoIDs, setJoinedRsoIDs] = useState([]);
  const [universityName, setUniversityName] = useState('');

  useEffect(() => {
    const storedUser = Cookies.get('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      if (!user || !user.universityID) return;

      try {
        const rsoRes = await axios.get(
          `http://localhost:8080/api/universities/${user.universityID}/rsos`
        );
        const approvedRsos = rsoRes.data.filter((rso) => rso.approved);
        setRsos(approvedRsos);

        if (approvedRsos.length > 0 && approvedRsos[0].university?.name) {
          setUniversityName(approvedRsos[0].university.name);
        }

        const joinedRes = await axios.get(
          `http://localhost:8080/api/users/${user.userID}/joined-rsos`
        );
        const joinedIDs = joinedRes.data.map((rso) => rso.rsoID);
        setJoinedRsoIDs(joinedIDs);
      } catch (err) {
        console.error('Error loading RSOs:', err);
      }
    };

    fetchData();
  }, [user]);

  const handleJoin = async (rsoID) => {
    try {
      await axios.post(`http://localhost:8080/api/rsos/${rsoID}/join`, {
        userID: user.userID,
      });
      alert('Joined successfully!');
      setJoinedRsoIDs((prev) => [...prev, rsoID]);
    } catch (err) {
      alert('Failed to join RSO.');
      console.error(err);
    }
  };

  const handleLeave = async (rsoID) => {
    try {
      await axios.delete(`http://localhost:8080/api/rsos/${rsoID}/leave`, {
        params: { userID: user.userID },
      });
      alert('Left RSO successfully!');
      setJoinedRsoIDs((prev) => prev.filter((id) => id !== rsoID));
    } catch (err) {
      alert('Failed to leave RSO.');
      console.error(err);
    }
  };

  return (
    <div className="bg-dark text-light min-vh-100 d-flex flex-column">
      <Navbar />

      <main className="container py-5 flex-grow-1">
        <h1 className="text-center text-info mb-4">
          Welcome{user?.firstName ? `, ${user.firstName}` : ''} to the Student Dashboard - {universityName || 'Your University'}
        </h1>

        {/* Upcoming Events */}
        <section id="events" className="mb-5">
          <StudentEvents />
        </section>

        {/* Request RSO */}
        <section id="request" className="mb-5">
          <RequestRSOForm userID={user?.userID} />
        </section>

        {/* Joined or Joinable RSOs */}
        <section id="rsos">
          <h3 className="text-warning mb-3">Current RSOs</h3>
          {rsos.length === 0 ? (
            <p className="text-muted">No RSOs available yet.</p>
          ) : (
            <div className="row">
              {rsos.map((rso) => (
                <div key={rso.rsoID} className="col-md-6 mb-3">
                  <div className="card bg-secondary text-white p-3">
                    <h5>{rso.name}</h5>
                    {joinedRsoIDs.includes(rso.rsoID) ? (
                      <button
                        onClick={() => handleLeave(rso.rsoID)}
                        className="btn btn-danger mt-2"
                      >
                        Leave RSO
                      </button>
                    ) : (
                      <button
                        onClick={() => handleJoin(rso.rsoID)}
                        className="btn btn-primary mt-2"
                      >
                        Join RSO
                      </button>
                    )}
                  </div>
                </div>
              ))}
            </div>
          )}
        </section>

        <div className="mt-4">
          <LogoutButton />
        </div>
      </main>

      <Footer />
    </div>
  );
};

export default StudentDashboard;