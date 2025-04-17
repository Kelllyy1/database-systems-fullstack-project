import React, { useEffect, useState } from "react";
import { getPendingRSOs, approveRSO } from "../api/rsoApi";
import 'bootstrap/dist/css/bootstrap.min.css';

const SuperRSOApprovalList = () => {
  const [pendingRSOs, setPendingRSOs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [approving, setApproving] = useState(null);

  useEffect(() => {
    getPendingRSOs()
      .then((res) => setPendingRSOs(res.data))
      .catch((err) => console.error("Failed to load RSOs:", err))
      .finally(() => setLoading(false));
  }, []);

  const handleApprove = async (rsoID) => {
    try {
      setApproving(rsoID);
      await approveRSO(rsoID);
      setPendingRSOs((prev) => prev.filter((rso) => rso.rsoID !== rsoID));
    } catch (err) {
      console.error("Approval failed:", err);
    } finally {
      setApproving(null);
    }
  };

  return (
    <div className="card bg-dark text-light shadow p-4 mb-4">
      <h3 className="text-warning mb-3">Pending RSO Approvals</h3>
      {loading ? (
        <p>Loading...</p>
      ) : pendingRSOs.length === 0 ? (
        <p className="text-muted">No pending RSOs.</p>
      ) : (
        pendingRSOs.map((rso) => (
          <div key={rso.rsoID} className="bg-secondary p-3 rounded mb-3">
            <h5>{rso.name}</h5>
            <button
              className="btn btn-success mt-2"
              disabled={approving === rso.rsoID}
              onClick={() => handleApprove(rso.rsoID)}
            >
              {approving === rso.rsoID ? "Approving..." : "Approve"}
            </button>
          </div>
        ))
      )}
    </div>
  );
};

export default SuperRSOApprovalList;
