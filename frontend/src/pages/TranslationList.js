import React, { Component } from 'react';
import { Container, Table, Button } from 'reactstrap';
import { Link } from 'react-router-dom';
import AppNavbar from './AppNavbar';

class TranslationList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            wordInFrench: '',
            translations: [],
            isLoading: true
        };
    }

    componentDidMount() {
        this.loadTranslations();
    }

    async loadTranslations() {
        const { match } = this.props;
        const { id } = match.params;

        // Fetch the word in French
        fetch(`/words/${id}`)
            .then(response => response.json())
            .then(data => {
                this.setState({ wordInFrench: data.wordInFrench });
            })
            .catch(error => console.log("Error fetching word in French: ", error));

        // Fetch translations
        fetch(`/words/${id}/translations`)
            .then(response => response.json())
            .then(data => this.setState({ translations: data, isLoading: false }))
            .catch(error => console.log("Error fetching translations: ", error));
    }

    render() {
        const { wordInFrench, translations, isLoading } = this.state;
        const { match } = this.props;
        const { id } = match.params;

        if (isLoading) {
            return <p>Loading translations...</p>;
        }

        return (
            <div>
                <AppNavbar />
                <Container fluid>
                    <h3>Word in French: {wordInFrench}</h3>
                    <h4>Translations:</h4>
                    <Table className="mt-4">
                        <tbody>
                            {translations.map(translation => (
                                <tr key={translation.id}>
                                    <td>{translation.wordInRomanian}</td>
                                    <td>
                                        <Button tag={Link} to={`/words/${id}/translations/${translation.id}/edit`} color="primary">Edit Translation</Button>{' '}
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </Table>
                    <Button tag={Link} to={{
                        pathname: `/words/${id}/translations/new`,
                        state: { translation: wordInFrench, wordInFrenchId: id}
                    }} color="success">Add Translation</Button>{' '}
                    <Button tag={Link} to="/words" color="primary">Back to Words</Button>
                </Container>
            </div>
        );
    }
}

export default TranslationList;
