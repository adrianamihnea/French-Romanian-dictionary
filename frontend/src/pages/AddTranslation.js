import React, { Component } from 'react';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class AddTranslation extends Component {
    constructor(props) {
        super(props);
        this.state = {
            translation: '',
            wordInFrenchId: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        const { id } = this.props.match.params;
        this.setState({ wordInFrenchId: id });
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        this.setState({ [name]: value });
    }

    async handleSubmit(event) {
        event.preventDefault();
        const { translation, wordInFrenchId } = this.state;

        console.log('wordInFrenchId:', wordInFrenchId); // Add this line for debugging


        await fetch(`/words/${wordInFrenchId}/translations/new`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ wordInFrenchId: wordInFrenchId, wordInRomanian: translation }),
        });

        this.props.history.push(`/words/${wordInFrenchId}/translations`);
    }

    render() {
        const { translation, wordInFrenchId } = this.state;
        const { wordInFrench } = this.props.location.state || {};

        return (
            <div>
                <AppNavbar />
                <Container>
                    <h3>Add Translation for {wordInFrench}</h3> {/* Display wordInFrench in the title */}
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for="wordInFrenchId">Word in French ID</Label>
                            <Input type="number" name="wordInFrenchId" id="wordInFrenchId" value={wordInFrenchId}
                                onChange={this.handleChange} autoComplete="wordInFrenchId" disabled />
                        </FormGroup>
                        <FormGroup>
                            <Label for="wordInRomanian">Word in Romanian</Label>
                            <Input type="text" name="translation" id="wordInRomanian" value={translation}
                                onChange={this.handleChange} autoComplete="wordInRomanian" />
                        </FormGroup>
                        <FormGroup>
                            <Button color="primary" type="submit">Save</Button>{' '}
                            <Button color="secondary" tag={Link} to="/words">Back</Button> {/* Back button */}
                        </FormGroup>
                    </Form>
                </Container>
            </div>
        );
    }
}

export default AddTranslation;
