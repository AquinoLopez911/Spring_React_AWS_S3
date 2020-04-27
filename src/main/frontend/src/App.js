import React, {useState, useEffect, useCallback} from 'react';
import {useDropzone} from 'react-dropzone'
import axios from "axios"; 

import logo from './logo.svg';

import './App.css';


//functional component "UserProfiles"
const UserProfiles = () => {

  // Declare a new state variable
  const [userProfiles, setuserProfiles]  = useState ([]);


  // api call to java back end (retrives all userProfiles)
  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile")
    .then(
      res => {
        console.log("profiles: ");
        console.log(userProfiles);
        console.log("response: ");
        console.log(res.data);

 
        //changes state from [] to the list of users and thier info
        setuserProfiles(res.data);

      });
  }//end FetchUserProfiles


  useEffect( () => {
    console.log("fetching profiles");
    fetchUserProfiles();   
    console.log(userProfiles);   
  }, []);

  //main return 
  return userProfiles.map((userProfile, index) => {

    //block variable because it changes for each user
    //setting the image for the CURRENT user profile
    let img;
    if(userProfile.userProfileImgLink) {
      img = <img
              src={`http://localhost:8080/api/v1/user-profile/${userProfile.userProfileId}/image/download`}
              alt={`${userProfile.username} img`}
            />;
    }else {
      img = <img
              src=""
              alt={`${userProfile.username} img`}
            />;
    }

    //for the current user profile in UserProfiles 
    return (
      <div key={index}>
        {img}
        <br></br>
        <br></br>
        <MyDropzone {...userProfile} setuserProfiles={setuserProfiles} />
        <h1>{userProfile.username}</h1>
        <p>{userProfile.userProfileId}</p>
        <br></br>
      </div>
    )
  })
};// end UserProfiles




function MyDropzone({userProfileId, setuserProfiles}) {

  const onDrop = useCallback(acceptedFiles => {
    // Do something with the files
    const file = acceptedFiles[0];
    console.log(file);
    
    //send file to backend as a multi part file
    const formdata = new FormData();
    
    formdata.append("file", file);

    axios.post(`http://localhost:8080/api/v1/user-profile/${userProfileId}/image/upload`,
    formdata,
    {
      headers : {
        "Content-Type" : "multipart/form-data",
      }
    }).then( res => {
      console.log("profile image uploaded succesfully");
      // response returns the same as the initaial data loaded 
      // now i need to change the state in the UserProfiles Compoenent 
      // but how ???
      console.log("changing state...")
      setuserProfiles(res.data)                                         // not working
      
      // window.location.reload(false);                           //refreshs whole page 

    }).catch(err => {
      console.log(err);
      console.log(userProfileId);
    });
  }, [userProfileId])

  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div style={{backgroundColor: "grey", padding: "2em", maxWidth: "70%", margin: "0 auto" , borderRadius: "10px"}} {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the files here ...</p> :
          <p>Drag 'n' drop profile image, or click to select profile image</p>
      }
    </div>
  )
}// end drop zone 









function App() {
  return (
    <div className="App">
       <UserProfiles />
    </div>
  );
}

export default App;
