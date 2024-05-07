import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import WordOfTheDayPopup from './WordOfTheDay'; // Import the WordOfTheDayPopup component
import axios from 'axios';

class WordList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            words: [],
            showWordOfTheDayPopup: true, // Initially set to true to display the popup
            wordOfTheDay: 'Example word', // Set the word of the day here
            userType: null // Initialize userType state
        };
        this.remove = this.remove.bind(this);
        this.hideWordOfTheDayPopup = this.hideWordOfTheDayPopup.bind(this);
    }

    componentDidMount() {
        this.loadWords();
        this.fetchUserType();
    }

    async loadWords() {
        fetch('/words')
            .then(response => response.json())
            .then(data => this.setState({ words: data }));
    }

    async remove(id) {
        await fetch(`/words/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedWords = [...this.state.words].filter(i => i.id !== id);
            this.setState({ words: updatedWords });
        });
    }

    async fetchUserType() {
        try {
            const response = await axios.get('/user-type');
            this.setState({ userType: response.data });
        } catch (error) {
            console.error('Error fetching user type:', error);
        }
    }

    getTranslations(id) {
        this.props.history.push(`/words/${id}/translations`); // Navigate to translations route
    }

    hideWordOfTheDayPopup() {
        this.setState({ showWordOfTheDayPopup: false });
    }

    render() {
        const { words, showWordOfTheDayPopup, wordOfTheDay, userType } = this.state;

        return (
            <div>
                <AppNavbar />
                <Container fluid>
                    {showWordOfTheDayPopup && <WordOfTheDayPopup word={wordOfTheDay} />}
                    {userType === 'ADMIN' && (
                        <div className="float-right">
                            <Button color="success" tag={Link} to="/words/new">Add Word</Button>
                        </div>
                    )}
                    <h3>Words</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th width="30%">Word</th>
                                {userType === 'admin' && <th width="20%">Actions</th>}
                            </tr>
                        </thead>
                        <tbody>
                            {words.map(word => (
                                <tr key={word.id}>
                                    <td style={{ whiteSpace: 'nowrap' }}>{word.wordInFrench}</td>
                                    {(userType === 'ADMIN' || userType === 'USER') && ( // Adjusted condition here
                                        <td>
                                        <Button size="sm" color="primary" tag={Link} to={`/words/${word.id}/translations`}>Get Translations</Button>
                                        {userType === 'ADMIN' && ( // Additional condition for delete button
                                                <ButtonGroup>
                                                <Button size="sm" color="primary" tag={Link} to={"/words/" + word.id}>Edit</Button>
                                                <Button size="sm" color="danger" onClick={() => this.remove(word.id)}>Delete</Button>
                                            </ButtonGroup>
                                        )}
                                        </td>
                                    )}
                                </tr>
                            ))}
                        </tbody>

                    </Table>
                </Container>
            </div>
        );
    }
}
export default WordList;
