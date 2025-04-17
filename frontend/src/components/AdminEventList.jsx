import React, { useEffect, useState } from "react";
import axios from "axios";
import Cookies from "js-cookie";

const AdminEventList = () => {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    const userCookie = Cookies.get("user");
    if (userCookie) {
      const user = JSON.parse(userCookie);
      axios.get(`http://localhost:8080/api/events/admin/${user.userID}`)
        .then((res) => setEvents(res.data))
        .catch((err) => console.error("Failed to load events", err));
    }
  }, []);

  const handleDelete = async (eventID) => {
    try {
      await axios.delete(`http://localhost:8080/api/events/${eventID}`);
      setEvents(events.filter((e) => e.eventID !== eventID));
    } catch (err) {
      console.error("Failed to delete event", err);
    }
  };

  return (
    <div className="mt-4">
      <h4 className="text-light">Your Events</h4>
      {events.map((event) => (
        <div key={event.eventID} className="card bg-secondary text-white mb-3">
          <div className="card-body">
            <h5 className="card-title">{event.name}</h5>
            <p className="card-text">{event.description}</p>
            <p className="card-text"><strong>Date:</strong> {event.date}</p>
            <button className="btn btn-danger" onClick={() => handleDelete(event.eventID)}>Delete</button>
          </div>
        </div>
      ))}
    </div>
  );
};

export default AdminEventList;
