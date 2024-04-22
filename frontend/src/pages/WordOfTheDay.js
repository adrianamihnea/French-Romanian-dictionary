import React, { useEffect, useState } from 'react';
import '../styles//WordOfTheDayPopup.css'; // Import the CSS file for styling

const WordOfTheDay = () => {
  const [word, setWord] = useState('');
  const [showPopup, setShowPopup] = useState(true); // State to control visibility of the popup
  
  useEffect(() => {
    const socket = new WebSocket('ws://localhost:8082/ws'); // Update the URL with your WebSocket server URL
  
    socket.onopen = () => {
      console.log('WebSocket connection established.');
      // Subscribe to the topic when the connection is opened
      socket.send(JSON.stringify({ type: 'subscribe', destination: '/topic/word-of-the-day' }));
    };
  
    socket.onmessage = (event) => {
      const message = JSON.parse(event.data);
      if (message.destination === '/topic/word-of-the-day') {
        // Update the word state with the received word of the day
        setWord(message.payload);
      }
    };
  
    socket.onerror = (error) => {
      console.error('WebSocket error:', error);
    };
  
    return () => {
      socket.close();
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

export default WordOfTheDay;
