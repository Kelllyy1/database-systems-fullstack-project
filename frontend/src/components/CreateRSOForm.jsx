import React, { useState } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';

const CreateRSOForm = ({ universityID }) => {
  const [name, setName] = useState('');
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const user = Cookies.get('user') ? JSON.parse(Cookies.get('user')) : null;

  const handleCreate = async () => {
    if (!user || !name.trim()) {
      setMessage('Please enter an RSO name and ensure you are logged in.');
      return;
    }

    setLoading(true);
    setMessage('');

    try {
      if (user.role === 'student') {
        const response = await axios.post('http://localhost:8080/api/rsos', null, {
          params: {
            name,
            universityId: universityID,
            userId: user.userID
          }
        });
        
        setMessage(`RSO "${response.data.name}" requested and pending approval.`);
      } else {
        const response = await axios.post('http://localhost:8080/api/rsos', null, {
          params: { name, universityId: universityID, userId: user.userID },
        });
        setMessage(`RSO "${response.data.name}" created and approved!`);
      }
      setName('');
    } catch (err) {
      console.error(err);
      if (err.response?.data) {
        setMessage(err.response.data);
      } else {
        setMessage('Failed to create or request RSO.');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card bg-dark text-white mt-4 p-4 shadow">
      <h4 className="mb-3">{user?.role === 'student' ? 'Request New RSO' : 'Create New RSO'}</h4>
      <input className="form-control mb-3" placeholder="RSO Name" value={name} onChange={(e) => setName(e.target.value)} />
      <button
        onClick={handleCreate}
        disabled={loading || !name.trim()}
        className="btn btn-outline-light"
      >
        {loading ? "Submitting..." : user?.role === 'student' ? 'Request RSO' : 'Create RSO'}
      </button>
      {message && <p className="text-warning mt-2">{message}</p>}
    </div>
  );
};

export default CreateRSOForm;
