// frontend/src/api/rsoApi.js
import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api';

export const getUniversityRSOs = (universityID) =>
  axios.get(`${BASE_URL}/universities/${universityID}/rsos`);

export const getJoinedRSOs = (userID) =>
  axios.get(`${BASE_URL}/users/${userID}/joined-rsos`);

export const joinRSO = (rsoID, userID) =>
  axios.post(`${BASE_URL}/rsos/${rsoID}/join`, { userID });

export const leaveRSO = (rsoID, userID) =>
  axios.delete(`${BASE_URL}/rsos/${rsoID}/leave`, {
    params: { userID },
  });

export const getPendingRSOs = () =>
  axios.get("http://localhost:8080/api/rsos/pending");

export const approveRSO = (rsoID) =>
  axios.put(`http://localhost:8080/api/rsos/${rsoID}/approve`);
