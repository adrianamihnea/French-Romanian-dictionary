import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import WordOfTheDayPopup from './WordOfTheDay'; // Import the WordOfTheDayPopup component

class WordList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            words: [],
            showWordOfTheDayPopup: true, // Initially set to true to display the popup
            wordOfTheDay: 'Example word', // Set the word of the day here
        };
        this.remove = this.remove.bind(this);
        this.hideWordOfTheDayPopup = this.hideWordOfTheDayPopup.bind(this);
    }

    componentDidMount() {
        this.loadWords();
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

    getTranslations(id) {
        this.props.history.push(`/words/${id}/translations`); // Navigate to translations route
    }

    hideWordOfTheDayPopup() {
        this.setState({ showWordOfTheDayPopup: false });
    }

    render() {
        const { words, showWordOfTheDayPopup, wordOfTheDay } = this.state;

        const wordList = words.map(word => {
            return <tr key={word.id}>
                <td style={{ whiteSpace: 'nowrap' }}>{word.wordInFrench}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/words/" + word.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(word.id)}>Delete</Button>
                        <Button size="sm" color="primary" tag={Link} to={`/words/${word.id}/translations`}>Get Translations</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar />
                <Container fluid>
                    {showWordOfTheDayPopup && <WordOfTheDayPopup word={wordOfTheDay} />}
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/words/new">Add Word</Button>
                    </div>
                    <h3>Words</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th width="30%">Word</th>
                            </tr>
                        </thead>
                        <tbody>
                            {wordList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}
export default WordList;
