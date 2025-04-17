import React from 'react';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import SuperRSOApprovalList from '../components/SuperRSOApprovalList';
import SuperPublicEventApprovalList from '../components/SuperPublicEventApprovalList';
import LogoutButton from '../components/LogoutButton';

const SuperDashboard = () => {
  return (
    <div className="bg-dark text-light min-vh-100">
      <Navbar />
      <div className="container py-5">
        <h1 className="text-center mb-5">Super Admin Dashboard</h1>
        <div id="rso-approval">
          <h3>Pending RSO Approvals</h3>
          <SuperRSOApprovalList />
        </div>
        <div id="event-approval" className="mt-5">
          <h3>Pending Public Events</h3>
          <SuperPublicEventApprovalList />
        </div>        
      </div>
      <LogoutButton />
      <Footer />
    </div>
  );
};

export default SuperDashboard;
