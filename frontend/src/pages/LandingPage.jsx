import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

const LandingPage = () => {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/events/public")
      .then((res) => setEvents(res.data))
      .catch((err) => console.error("Error loading public events:", err));
  }, []);

  return (
    <div className="bg-dark text-light min-vh-100 d-flex flex-column">
      {/* Navbar */}
      <nav className="navbar navbar-expand-lg navbar-dark bg-black border-bottom border-purple">
        <div className="container-fluid px-4">
          <span className="navbar-brand fw-bold text-white">
            🎓 Campus Connect
          </span>
          <div className="ms-auto d-flex gap-2">
            <Link to="/login" className="btn btn-outline-light">
              Login
            </Link>
            <Link to="/signup" className="btn btn-light text-dark">
              Sign Up
            </Link>
          </div>
        </div>
      </nav>

      {/* Hero Section */}
      <header className="text-center py-5">
        <h1 className="display-4 fw-bold text-info">Welcome to Campus Connect</h1>
        <p className="lead">Explore events happening on your campus today.</p>
      </header>

      {/* Public Events */}
      <section className="container mb-5">
        <h2 className="text-info mb-4"> Featured Public Events</h2>
        {events.length === 0 ? (
          <p className="text-muted">No public events available yet.</p>
        ) : (
          <div className="row">
            {events.map((event) => (
              <div key={event.eventID} className="col-md-6 mb-4">
                <div className="card bg-secondary text-white shadow h-100">
                  <div className="card-body">
                    <h5 className="card-title fw-bold">{event.name}</h5>
                    <p className="card-text">{event.description}</p>
                    <p className="card-text">
                      <small>
                        <strong>Date:</strong> {event.date}
                      </small>
                    </p>
                    <p className="card-text">
                      <small>
                        <strong>Location:</strong> {event.location?.name || 'TBD'}
                      </small>
                    </p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </section>

      {/* Footer */}
      <footer className="bg-black text-center text-light py-3 mt-auto">
        <small>© {new Date().getFullYear()} Campus Connect. All rights reserved.</small>
      </footer>
    </div>
  );
};

export default LandingPage;
