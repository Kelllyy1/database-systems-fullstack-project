class UserModel {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    phoneNumber: string;
    dob: Date;
    universityID: number;
    role?: string;
    createdAt?: Date;
    updatedAt?: Date;
  
    constructor(
      id: number,
      firstName: string,
      lastName: string,
      email: string,
      password: string,
      phoneNumber: string,
      dob: Date,
      universityID: number,
      role?: string,
      createdAt?: Date,
      updatedAt?: Date
    ) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
      this.phoneNumber = phoneNumber;
      this.dob = dob;
      this.universityID = universityID;
      this.role = role;
      this.createdAt = createdAt;
      this.updatedAt = updatedAt;
    }
  }
  
  export default UserModel;
  