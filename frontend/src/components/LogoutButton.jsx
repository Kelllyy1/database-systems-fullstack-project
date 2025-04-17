import React from 'react';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';

const LogoutButton = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    Cookies.remove('user');
    navigate('/login');
  };

  return (
    <button onClick={handleLogout} className="btn btn-secondary">
      Log Out
    </button>
  );
};

export default LogoutButton;
