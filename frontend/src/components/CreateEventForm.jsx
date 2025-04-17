import React, { useState, useEffect } from "react";
import axios from "axios";
import Cookies from "js-cookie";

const CreateEventForm = () => {
  const cookie = Cookies.get("user");
  const user = cookie ? JSON.parse(cookie) : null;

  const [form, setForm] = useState({
    name: "",
    description: "",
    date: "",
    contactEmail: "",
    contactPhone: "",
    type: "",
    visibility: "",
    locationID: "",
    rsoID: ""
  });

  const [locations, setLocations] = useState([]);
  const [rsos, setRsos] = useState([]);
  const [message, setMessage] = useState("");

  useEffect(() => {
    axios.get("http://localhost:8080/api/locations")
      .then((res) => setLocations(res.data))
      .catch((err) => console.error("Error loading locations:", err));

    if (user?.userID) {
      axios.get(`http://localhost:8080/api/rsos/owned-by/${user.userID}`).then((res) => {
        const approvedRsos = res.data.filter(rso => rso.approved);
        setRsos(approvedRsos);
      });
    }
  }, [user?.userID]);

  const handleSubmit = async () => {
    const {
      name, description, date, contactEmail, contactPhone, type, visibility, locationID, rsoID
    } = form;

    if (!name || !description || !date || !contactEmail || !type || !visibility || !locationID || !contactPhone) {
      setMessage("Please fill in all required fields.");
      return;
    }

    if (visibility === "rso" && !rsoID) {
      setMessage("Please select an RSO for RSO-only events.");
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/api/events", null, {
        params: {
          name,
          description,
          date,
          contactEmail,
          contactPhone,
          type,
          visibility,
          createdById: user.userID,
          approvedById: user.userID,
          locationId: locationID,
          rsoId: visibility === "rso" ? rsoID : null
        }
      });
      

      setMessage("Event created successfully!");
      setForm({
        name: "",
        description: "",
        date: "",
        contactEmail: "",
        contactPhone: "",
        type: "",
        visibility: "",
        locationID: "",
        rsoID: ""
      });
    } catch (err) {
      console.error("Error creating event:", err);
      setMessage("Failed to create event.");
    }
  };

  return (
    <div className="card bg-dark text-white p-4 mt-4 shadow">
      <h4 className="mb-3">Create New Event</h4>
      <input className="form-control mb-2" placeholder="Event Name" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} />
      <input className="form-control mb-2" type="date" value={form.date} onChange={(e) => setForm({ ...form, date: e.target.value })} />
      <input className="form-control mb-2" placeholder="Contact Email" value={form.contactEmail} onChange={(e) => setForm({ ...form, contactEmail: e.target.value })} />
      <input className="form-control mb-2" placeholder="Contact Phone" value={form.contactPhone} onChange={(e) => setForm({ ...form, contactPhone: e.target.value })} />
      <input className="form-control mb-2" placeholder="Type (e.g. tech, social)" value={form.type} onChange={(e) => setForm({ ...form, type: e.target.value })} />
      <select className="form-control mb-2" value={form.visibility} onChange={(e) => setForm({ ...form, visibility: e.target.value })}>
        <option value="">Select Visibility</option>
        <option value="public">Public</option>
        <option value="private">Private</option>
        <option value="rso">RSO</option>
      </select>
      {form.visibility === "rso" && (
        <select className="form-control mb-2" value={form.rsoID} onChange={(e) => setForm({ ...form, rsoID: parseInt(e.target.value) })}>
          <option value="">Select RSO</option>
          {rsos.map((rso) => (
            <option key={rso.rsoID} value={rso.rsoID}>{rso.name}</option>
          ))}
        </select>
      )}
      <select className="form-control mb-2" value={form.locationID} onChange={(e) => setForm({ ...form, locationID: parseInt(e.target.value) })}>
        <option value="">Select Location</option>
        {locations.map((loc) => (
          <option key={loc.locationID} value={loc.locationID}>{loc.name}</option>
        ))}
      </select>
      <textarea className="form-control mb-3" placeholder="Description" value={form.description} onChange={(e) => setForm({ ...form, description: e.target.value })} />
      <button className="btn btn-outline-light w-100" onClick={handleSubmit}>Submit Event</button>
      {message && <p className="text-warning mt-3">{message}</p>}
    </div>
  );
};

export default CreateEventForm;
