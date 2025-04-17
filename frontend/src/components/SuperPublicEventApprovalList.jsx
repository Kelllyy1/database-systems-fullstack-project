import React, { useEffect, useState } from "react";
import axios from "axios";
import Cookies from "js-cookie";

const SuperPublicEventApprovalList = () => {
  const [events, setEvents] = useState([]);
  const [user, setUser] = useState(null);

  useEffect(() => {
    const cookie = Cookies.get("user");
    if (cookie) {
      setUser(JSON.parse(cookie));
    }

    axios.get("http://localhost:8080/api/events/pending-public")
      .then((res) => setEvents(res.data))
      .catch((err) => console.error("Error loading public events:", err));
  }, []);

  const handleApprove = async (eventID) => {
    try {
      await axios.put(`http://localhost:8080/api/events/approve/${eventID}`, null, {
        params: { approvedById: user.userID }
      });

      setEvents(events.filter((e) => e.eventID !== eventID));
    } catch (err) {
      console.error("Error approving event:", err);
    }
  };

  return (
    <div className="card bg-dark text-light shadow p-4 mb-4">
      <h3 className="text-info mb-3">Pending Public Events</h3>
      {events.length === 0 ? (
        <p className="text-muted">No pending public events.</p>
      ) : (
        events.map((event) => (
          <div key={event.eventID} className="card bg-secondary mb-3 text-white p-3">
            <h5 className="fw-bold">{event.name}</h5>
            <p>{event.description}</p>
            <p><strong>Date:</strong> {new Date(event.date).toLocaleDateString()}</p>
            <p><strong>Visibility:</strong> {event.visibility}</p>
            <button
              className="btn btn-success w-100 mt-2"
              onClick={() => handleApprove(event.eventID)}
            >
              Approve
            </button>
          </div>
        ))
      )}
    </div>
  );
};

export default SuperPublicEventApprovalList;
