import React from 'react';
import Cookies from 'js-cookie';

const HomePage = () => {
  const cookie = Cookies.get('user');
  const user = cookie ? JSON.parse(cookie) : null;

  if (!user) {
    return <h2 className="text-center mt-10 text-red-600">You must be logged in to view this page.</h2>;
  }

  const { firstName, role, email } = user;

  const renderContentByRole = () => {
    switch (role) {
      case 'student':
        return <p className="text-blue-600">Welcome, student! You can view and join RSOs.</p>;
      case 'admin':
        return <p className="text-green-600">Welcome, admin! You can create RSOs and post events.</p>;
      case 'super':
        return <p className="text-purple-600">Welcome, super admin! You have full access including RSO approvals.</p>;
      default:
        return <p className="text-gray-600">Role not recognized. Please contact support.</p>;
    }
  };

  const handleLogout = () => {
    Cookies.remove('user');
    window.location.href = '/login';
  };

  return (
    <div className="p-6 max-w-xl mx-auto mt-10 bg-white rounded-xl shadow-md space-y-4">
      <h1 className="text-2xl font-bold">Welcome, {firstName || 'Guest'}!</h1>
      <p>You are logged in with email: <span className="font-medium">{email || 'N/A'}</span></p>
      <p className="italic">Role: {role}</p>
      <div>{renderContentByRole()}</div>
      <button 
        onClick={handleLogout}
        className="mt-4 bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600">
        Log Out
      </button>
    </div>
  );
};

export default HomePage;