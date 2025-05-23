// Navbar.jsx
import React from 'react';

const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
      <div className="container-fluid">
        <a className="navbar-brand" href="#welcome">🎓 Campus Connect</a>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <a className="nav-link" href="#events">Upcoming Events</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#request">Request New RSO</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#current">Current RSOs</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
