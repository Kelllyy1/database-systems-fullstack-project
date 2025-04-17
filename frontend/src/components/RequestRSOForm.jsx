import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';

const RequestRSOForm = () => {
  const [rsoName, setRsoName] = useState('');
  const [message, setMessage] = useState('');
  const [user, setUser] = useState(null);

  useEffect(() => {
    const cookie = Cookies.get("user");
    if (cookie) {
      setUser(JSON.parse(cookie));
    }
  }, []);

  const handleSubmit = async () => {
    if (!rsoName.trim() || !user?.userID) {
      setMessage('Please enter an RSO name and ensure you are logged in.');
      return;
    }

    try {
      const payload = {
        name: rsoName,
        userID: user.userID  // Ensure userID is extracted correctly from user object
      };
      
      const response = await axios.post('http://localhost:8080/api/rsos/request', null, {
        params: {
          name: rsoName,
          userID: user.userID
        }
      });
      

      if (response.status === 200) {
        setMessage('Request submitted successfully!');
        setRsoName('');
      } else {
        throw new Error('Failed to submit request');
      }
    } catch (err) {
      console.error('Request RSO error:', err);
      setMessage('Failed to submit request. Please try again.');
    }
  };

  return (
    <div className="card bg-light shadow-sm p-3 mb-4">
      <div className="card-body">
        <h5 className="card-title">Request New RSO</h5>
        <input
          type="text"
          className="form-control mb-2"
          placeholder="Enter RSO Name"
          value={rsoName}
          onChange={(e) => setRsoName(e.target.value)}
        />
        <button onClick={handleSubmit} className="btn btn-primary w-100">Request RSO</button>
        {message && <div className="alert alert-info mt-3">{message}</div>}
      </div>
    </div>
  );
};

export default RequestRSOForm;
