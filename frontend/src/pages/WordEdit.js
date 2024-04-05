import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class WordEdit extends Component {
    constructor(props) {
        super(props);
        this.state = {
            item: {},
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleDelete = this.handleDelete.bind(this);
    }

    async componentDidMount() {
        const { id } = this.props.match.params;
        if (id !== 'new') {
            const word = await (await fetch(`/words/${id}`)).json();
            this.setState({ item: word });
        } else {
            // For new words, set the item to an empty object
            this.setState({ item: {} });
        }
    }
    

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = { ...this.state.item };
        item[name] = value;
        this.setState({ item });
    }

    async handleSubmit(event) {
        event.preventDefault();
        const { item } = this.state;

        if (item.id) {
            await fetch(`/words/${item.id}`, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(item),
            });
        } else {
            await fetch('/words/new', { // Modified endpoint for adding a new word
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(item),
            });
        }

        this.props.history.push('/words');
    }

    async handleDelete() {
        const { item } = this.state;

        if (item.id) {
            await fetch(`/words/${item.id}`, {
                method: 'DELETE'
            });
        }

        this.props.history.push('/words');
    }

    render() {
        const { item } = this.state;
        const title = <h2>{item.id ? 'Edit Word' : 'Add Word'}</h2>;

        return (
            <div>
                <AppNavbar />
                <Container>
                    {title}
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for="name">Word in French</Label>
                            <Input type="text" name="wordInFrench" id="wordInFrench" value={item.wordInFrench || ''}
                                onChange={this.handleChange} autoComplete="wordInFrench" />
                        </FormGroup>
                        <FormGroup>
                            <Button color="primary" type="submit">Save</Button>{' '}
                            <Button color="secondary" tag={Link} to="/words">Cancel</Button>{' '}
                            {item.id &&
                                <Button color="danger" onClick={this.handleDelete}>Delete</Button>
                            }
                        </FormGroup>
                    </Form>
                </Container>
            </div>
        );
    }
}

export default withRouter(WordEdit);
