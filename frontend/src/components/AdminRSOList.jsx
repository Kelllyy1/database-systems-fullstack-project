import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { approveRSO } from '../api/rsoApi'; // Assuming this import is correctly set up

const AdminRSOList = ({ adminID }) => {
    const [rsos, setRsos] = useState([]);

    // Function to fetch RSOs
    const fetchRSOs = React.useCallback(async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/rsos/owned-by/${adminID}`);
            setRsos(response.data);
        } catch (err) {
            console.error('Failed to fetch RSOs:', err);
        }
    }, [adminID]);

    // Effect to load RSOs on component mount and adminID change
    useEffect(() => {
        if (adminID) {
            fetchRSOs();
        }
    }, [adminID, fetchRSOs]);

    const handleApprove = async (rsoID) => {
        try {
            await approveRSO(rsoID);
            fetchRSOs(); // Refresh the list of RSOs after approval
        } catch (err) {
            console.error('Failed to approve RSO:', err);
        }
    };

    return (
        <div>
            {rsos.length === 0 ? (
                <p>No RSOs created yet.</p>
            ) : (
                rsos.map((rso) => (
                    <div key={rso.rsoID} className="card bg-secondary text-white mb-3 shadow">
                        <div className="card-body">
                            <h5 className="card-title">{rso.name}</h5>
                            <p className="card-text">University: {rso.university.name}</p>
                            <p className="card-text">Members: {rso.memberCount}</p>
                            <p className="card-text">
                                Status: {rso.approved ? 'Approved' : 'Pending Approval'}
                            </p>
                            {!rso.approved && (
                                <button
                                    className="btn btn-success"
                                    onClick={() => handleApprove(rso.rsoID)}
                                >
                                    Approve
                                </button>
                            )}
                        </div>
                    </div>
                ))
            )}
        </div>
    );
};

export default AdminRSOList;
