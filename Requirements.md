# College Event Management System - Requirements Document

## 1. Introduction
This document servers to outline the application's purpose, target audience, key features, and functionalities.

### 1.1 Purpose
The **College Event Management System** is a web-based platform designed to facilitate event **creation, management, and participation** for university students, **RSOs (Registered Student Organizations), and university administrators**. It enables event discovery, seamless registration, and communication while ensuring administrative oversight.

### 1.2 Scope
The application provides the following functionalities:
- 📌 **Students** can browse, register, and participate in events.
- 📌 **RSO Admins** can create and manage RSO-exclusive events.
- 📌 **University Administrators** (Super Admins) can approve public events and oversee event management.
- 📌 **Location-based event tracking** ensures efficient event organization. [Stretch Goal]
- 📌 **Comments & Ratings** allow student engagement and feedback. [Stretch Goal]

### 1.3 Target Audience
- 🎓 **University students** looking for events to participate in.
- 🏛️ **RSO leaders** responsible for organizing student activities.
- 👨‍💼 **University administrators** overseeing event approvals and policies.

---

## 2. System Features & Functionalities

### 2.1 User Management
- 🔐 **User Roles & Authentication:**
  - Three user types: **Students, Admins (RSO Leaders), and Super Admins**.
  - Users register via **university email verification**.
  - Profile updates available for all users.

### 2.2 Event Management
- 📝 **Event Creation:**
  - Admins (RSO Leaders) can create events with details: **name, description, date, time, location, contact info, and type (Public, Private, RSO-specific).**
- 👀 **Event Visibility:**
  - **Public Events**: Open to all students.
  - **Private Events**: Restricted to university students.
  - **RSO Events**: Accessible only by RSO members.
- ✅ **Event Approval Workflow:**
  - **Super Admins must approve public events** before publication.
  - **Private and RSO events do not require approval.**

### 2.3 Event Participation
- 🎟️ **Event Registration & RSVP:**
  - Students can register for events.
  - Organizers can view participant lists.
- 📍 **Attendance Tracking:** [Stretch Goal]
  - Check-in feature for attendees.

### 2.4 RSO Management
- 🔗 **RSO Creation & Membership:**
  - Students can join multiple RSOs.
  - RSOs require **at least 5 students** and **one admin**.
- 🎭 **Role-based Membership:**
  - Roles include **Member, Officer, and President**.
  - Admins manage membership requests.

### 2.5 Location Management
- 📌 **Assigning Event Locations:**
  - Events are linked to specific university locations.
  - Prevents **double-booking conflicts**.
- 🌎 **Location Details:**
  - Includes **name, latitude, longitude**.

### 2.6 Engagement Features
- 💬 **Comments & Ratings:**
  - Users can **comment on and rate events** (1-5 scale ⭐⭐⭐⭐⭐).
  - Comments can be reported for **moderation**.

---

## 3. System Constraints & Assumptions
- **Authentication is based on university email verification.**
- **Users cannot create RSOs without meeting the minimum membership requirement.**
- **Events must have a defined start and end time.**
- **Public events require approval from Super Admins.**
- **System should scale to accommodate thousands of users and events.**

---

## 4. Technologies & Tools
- 🎨 **Frontend:** React, .
- ⚙️ **Backend:** Node.js --> Transitioning to Java.
- 🗄️ **Database:** MongoDB (for front-end testing) / MySQL or Oracle (final storage).
- ☁️ **Hosting:** TBD.

---

## 5. Future Enhancements [More Stretch Goals]
- 📱 **Mobile App Version** for event tracking.
- 📅 **Integration with Calendar APIs** (Google Calendar, Outlook) for event syncing.
- 🤖 **AI-Based Event Recommendations** based on user interests.
- 🏆 **Gamification Features** (badges for frequent participation).

---

## 6. Conclusion
The **College Event Management System** simplifies **event organization and participation**, creating a **centralized** and **engaging** platform for students, RSOs, and administrators. This system ensures that events are **well-managed**, **accessible**, and **moderated**, fostering a more active university community.

---
