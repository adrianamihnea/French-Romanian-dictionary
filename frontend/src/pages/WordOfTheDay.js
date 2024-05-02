import React, { useEffect, useState } from 'react';
import '../styles/WordOfTheDayPopup.css'; // Import the CSS file for styling

const WordOfTheDayPopup = () => {
  const [word, setWord] = useState('');
  const [showPopup, setShowPopup] = useState(true); // State to control visibility of the popup
  const [ws, setWs] = useState(null);

  useEffect(() => {
    const socket = new WebSocket('ws://localhost:8080');
    setWs(socket);
  
    socket.onopen = () => {
      console.log('Connected to WebSocket server');
      // Send a message to request a random word
      socket.send(JSON.stringify({ type: 'randomWordRequest' }));
    };
  
    socket.onmessage = (event) => {
      const receivedData = event.data;
      setWord(receivedData); // Set the received data directly as text
    };
    
  
    return () => {
      if (ws) {
        ws.close();
      }
    };
  }, []);
  

  const closePopup = () => {
    setShowPopup(false);
  };

  return (
    <>
      {showPopup && (
        <div className="word-of-the-day-popup">
          <div className="popup-content">
            <button className="close-button" onClick={closePopup}>X</button>
            <h2>Word of the Day</h2>
            <p>{word}</p>
          </div>
        </div>
      )}
    </>
  );
};

export default WordOfTheDayPopup;
