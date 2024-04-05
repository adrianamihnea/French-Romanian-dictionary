import React, { Component } from 'react';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class EditTranslation extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: '',
            translationId: '',
            updatedTranslation: '',
            wordInFrench: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        const { id, translationId } = this.props.match.params;
        this.setState({ id: id, translationId: translationId });

        // Fetch the word in French using the id
        this.fetchWordInFrench(id);
    }

    async fetchWordInFrench(id) {
        try {
            const response = await fetch(`/words/${id}`);
            if (!response.ok) {
                throw new Error('Failed to fetch word in French');
            }
            const data = await response.json();
            this.setState({ wordInFrench: data.wordInFrench });
        } catch (error) {
            console.error('Error fetching word in French:', error);
        }
    }

    handleChange(event) {
        const { name, value } = event.target;
        this.setState({ [name]: value });
    }

    async handleSubmit(event) {
        event.preventDefault();
        const { id, translationId, updatedTranslation } = this.state;

        try {
            await fetch(`/words/${id}/translations/${translationId}/edit`, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ wordInRomanian: updatedTranslation }),
            });

            this.props.history.push(`/words/${id}/translations`);
        } catch (error) {
            console.error('Error updating translation:', error);
        }
    }

    render() {
        const { id, updatedTranslation, wordInFrench } = this.state;

        return (
            <div>
                <AppNavbar />
                <Container>
                    <h3>Edit Translation for {wordInFrench}</h3>
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for="id">Word in French ID</Label>
                            <Input type="number" name="id" id="id" value={id}
                                onChange={this.handleChange} autoComplete="id" disabled />
                        </FormGroup>
                        <FormGroup>
                            <Label for="updatedTranslation">Word in Romanian</Label>
                            <Input type="text" name="updatedTranslation" id="wordInRomanian" value={updatedTranslation}
                                onChange={this.handleChange} autoComplete="wordInRomanian" />
                        </FormGroup>
                        <FormGroup>
                            <Button color="primary" type="submit">Save</Button>{' '}
                            <Button color="secondary" tag={Link} to="/words">Back</Button>
                        </FormGroup>
                    </Form>
                </Container>
            </div>
        );
    }
}

export default EditTranslation;
