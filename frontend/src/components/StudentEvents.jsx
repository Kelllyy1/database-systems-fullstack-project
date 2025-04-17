import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';

const StudentEvents = () => {
  const [events, setEvents] = useState([]);
  const [userID, setUserID] = useState(null);

  useEffect(() => {
    const userCookie = Cookies.get("user");
    if (userCookie) {
      const user = JSON.parse(userCookie);
      setUserID(user.userID);

      axios.get(`http://localhost:8080/api/events/student/${user.userID}`)
        .then(res => setEvents(res.data))
        .catch(err => console.error("Failed to load events", err));
    }
  }, []);

  return (
    <div className="card">
      <div className="card-body">
        <h5 className="card-title">Your Upcoming Events</h5>
        {events.length === 0 ? (
          <p>No upcoming events found.</p>
        ) : (
          events.map((event, index) => (
            <div key={event.id || index} className="mb-2"> {/* Ensure unique key */}
              <strong>{event.name}</strong>
              <p>{event.description}</p>
              <p>Date: {new Date(event.date).toLocaleDateString()}</p>
              <p>Visibility: {event.visibility}</p> {/* Display visibility */}
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default StudentEvents;
