import React, {useState, useEffect, useCallback, useRef} from 'react';
import {useDropzone} from 'react-dropzone'
import axios from "axios"; 

import './App.css';

function usePrevious(value) {
  // The ref object is a generic container whose current property is mutable ...
  // ... and can hold any value, similar to an instance property on a class
  const ref = useRef();
  
  // Store current value in ref
  useEffect(() => {
    ref.current = value;
  }, [value]); // Only re-run if value changes
  
  // Return previous value (happens before update in useEffect above)
  return ref.current;
}

//functional component "UserProfiles"
const UserProfiles = () => {

  const initalState = [];

  // Declare a new state variable
  const [userProfiles, setuserProfiles]  = useState(initalState);

  const prevProfiles = usePrevious(userProfiles);
  // console.log("random userProfile call ")
  // console.log(userProfiles)
  // console.log(prevProfiles)

  // api call to java back end (retrives all userProfiles)
  // console.log(userProfiles);
  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile")
    .then(
      res => {
        // console.log("response: ");
        // console.log(res.data);
        //changes state from [] to the list of users and thier info
        setuserProfiles(res.data);

        

      });
  }//end FetchUserProfiles
  console.log(userProfiles);


  useEffect( () => {
    console.log("fetching profiles");
    fetchUserProfiles();  
    if(userProfiles !== initalState) {
      fetchUserProfiles()
    }
  }, []);

  //main return
  // make async 
  return userProfiles.map((userProfile, index) => {
    
    //block variable because it changes for each user
    //setting the image for the CURRENT user profile
    let img;
    if(userProfile.userProfileImgLink !== prevProfiles[index]) {
      
      img = <div>
              <img
                src={`http://localhost:8080/api/v1/user-profile/${userProfile.userProfileId}/${userProfile.userProfileImgLink}/download`}
                // had to make a diffetent get path because the userProfileId doesnt change
                // i included the profileImgLink in the new path because that will make the rerender happen when comaring the doms
                // src={`http://localhost:8080/api/v1/user-profile/${userProfile.userProfileId}/image/download`}
                alt={`${userProfile.userProfileImgLink} img`}
              />
            </div>

    }else {
      img = <img
              src=""
              alt={`${userProfile.userProfileImgLink} img`}
            />;
    }
    //for the current user profile in UserProfiles 
    return (
      <div key={index}>
        {img}
        <br></br>
        <br></br>
        <MyDropzone {...userProfile} setuserProfiles={setuserProfiles} previousProfiles={prevProfiles} />
        <h1>{userProfile.username}</h1>
        <p>{userProfile.userProfileId}</p>
        <br></br>
      </div>
    )
  })
};// end UserProfiles




function MyDropzone({userProfileId, setuserProfiles, prevProfiles}) {

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
      console.log(res)
      if(prevProfiles !== res.data) {
        console.log("not equal lists")
        setuserProfiles(res.data)                             
      }

    }).catch(err => {
      console.log(err);
      console.log(userProfileId);
    });
  }, [])

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

bubbleSort(arr) { 
  let n = arr.length; 
  for (let i = 0; i < n-1; i++) 
    for (let j = 0; j < n-i-1; j++) 
      if (arr[j] > arr[j+1]) { 
        // swap arr[j+1] and arr[j] 
        let temp = arr[j]; 
        arr[j] = arr[j+1]; 
        arr[j+1] = temp; 
      } 
} 

for(let i=0; i=>20; i++) {
  console.log('anthony is sus')
}



function App() {
  return (
    <div className="App">
       <UserProfiles />
    </div>
  );
}

export default App;
