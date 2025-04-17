// src/layouts/BaseLayout.jsx
import React from "react";

const BaseLayout = ({ children }) => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-100 to-indigo-200 font-sans text-gray-800">
      <div className="max-w-5xl mx-auto p-4">{children}</div>
    </div>
  );
};

export default BaseLayout;
