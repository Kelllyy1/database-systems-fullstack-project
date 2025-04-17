import React from 'react';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import CreateRSOForm from '../components/CreateRSOForm';
import AdminEventList from '../components/AdminEventList';
import CreateEventForm from '../components/CreateEventForm';
import AdminRSOList from '../components/AdminRSOList';
import LogoutButton from '../components/LogoutButton';
import Cookies from 'js-cookie';

const AdminDashboard = () => {
  const userCookie = Cookies.get("user");
  const user = userCookie ? JSON.parse(userCookie) : null;
  
  return (
    <div className="bg-dark text-light min-vh-100">
      <Navbar />
      <div className="container py-5">
        <h1 className="text-center mb-5">Admin Dashboard</h1>
        <div id="events">
          <h3>Manage Your Events</h3>
          <AdminEventList />
        </div>
        <div id="rso" className="mt-5">
          <h3>Create an RSO</h3>
          <CreateRSOForm />
        </div>
        {user && (
          <div id="admin-rsos" className="mt-5">
            <AdminRSOList adminID={user.userID} />
          </div>
        )}
        <div id="create-event" className="mt-5">
          <h3>Create an Event</h3>
          <CreateEventForm />
        </div>
        <LogoutButton />        
      </div>
      <Footer />
    </div>
  );
};

export default AdminDashboard;
