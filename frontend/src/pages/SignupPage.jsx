import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const SignupPage = () => {
  const [universities, setUniversities] = useState([]);
  const [formData, setFormData] = useState({ firstName: '', lastName: '', email: '', password: '', dob: '', phoneNumber: '', universityID: '' });
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    axios.get("http://localhost:8080/api/universities").then(res => setUniversities(res.data));
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const payload = {
      ...formData,
      university: { universityID: parseInt(formData.universityID) }
    };
    axios.post("http://localhost:8080/api/auth/signup", payload)
      .then(() => navigate('/login'))
      .catch(() => setMessage('Signup failed. Please try again.'));
  };

  return (
    <div className="bg-dark text-white min-vh-100 d-flex align-items-center justify-content-center">
      <form onSubmit={handleSubmit} className="bg-secondary p-5 rounded w-100" style={{ maxWidth: '600px' }}>
        <h2 className="text-center mb-4">Sign Up</h2>
        <div className="row">
          <div className="col"><input name="firstName" className="form-control mb-3" placeholder="First Name" onChange={handleChange} required /></div>
          <div className="col"><input name="lastName" className="form-control mb-3" placeholder="Last Name" onChange={handleChange} required /></div>
        </div>
        <input name="email" className="form-control mb-3" placeholder="Email" onChange={handleChange} required />
        <input name="password" type="password" className="form-control mb-3" placeholder="Password" onChange={handleChange} required />
        <input name="dob" type="date" className="form-control mb-3" onChange={handleChange} required />
        <input name="phoneNumber" className="form-control mb-3" placeholder="Phone Number" onChange={handleChange} required />
        <select name="universityID" className="form-control mb-3" onChange={handleChange} required>
          <option value="">Select a University</option>
          {universities.map(uni => <option key={uni.universityID} value={uni.universityID}>{uni.name}</option>)}
        </select>
        <button className="btn btn-primary w-100">Create Account</button>
        {message && <p className="text-warning text-center mt-3">{message}</p>}
      </form>
    </div>
  );
};

export default SignupPage;
