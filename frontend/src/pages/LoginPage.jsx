import React, { useState } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post('http://localhost:8080/api/auth/login', { email, password });
      Cookies.set('user', JSON.stringify(res.data), { expires: 1 });
      const role = res.data.role;
      if (role === 'student') navigate('/student');
      else if (role === 'admin') navigate('/admin');
      else if (role === 'super') navigate('/super');
    } catch {
      setMessage('Login failed. Please check credentials.');
    }
  };

  return (
    <div className="bg-dark text-white min-vh-100 d-flex align-items-center justify-content-center">
      <form onSubmit={handleLogin} className="bg-secondary p-5 rounded w-100" style={{ maxWidth: '400px' }}>
        <h2 className="text-center mb-4">Log In</h2>
        <input type="email" className="form-control mb-3" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} required />
        <input type="password" className="form-control mb-3" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} required />
        <button className="btn btn-primary w-100">Login</button>
        {message && <p className="text-warning text-center mt-3">{message}</p>}
      </form>
    </div>
  );
};

export default LoginPage;
