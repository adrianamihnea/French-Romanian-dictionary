import React, { useEffect, useState } from 'react';
import '../styles/WordOfTheDayPopup.css'; // Import the CSS file for styling

const WordOfTheDayPopup = () => {
  const [word, setWord] = useState('');
  const [showPopup, setShowPopup] = useState(true); // State to control visibility of the popup

  useEffect(() => {
    fetchRandomWord();
  }, []);

  const fetchRandomWord = async () => {
    try {
      const response = await fetch('/words/randomWord'); // Assuming your backend endpoint is /randomWord
      if (response.ok) {
        const word = await response.text(); // Read the response as text
        setWord(word); // Set the word directly
      } else {
        console.error('Failed to fetch random word');
      }
    } catch (error) {
      console.error('Error fetching random word:', error);
    }
  };

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
