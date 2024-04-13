import React, { Component } from 'react';
import { Container, Row, Col, Form, FormGroup, Label, Input, Button } from 'reactstrap';
import { withRouter } from 'react-router-dom';

class Home extends Component {
    handleLogin = async () => {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        
        try {
            const response = await fetch('/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                // Login successful, redirect to the list of words
                this.props.history.push('/words');
            } else {
                // Handle login error (e.g., display error message)
            }
        } catch (error) {
            console.error('Error logging in:', error);
            // Handle error (e.g., display error message)
        }
    }

    handleRegister = async () => {
        // Similar to login, send a POST request to /createUser endpoint with registration data
        // Handle the response accordingly
    }

    render() {
        return (
            <div>
                <Container>
                    <Row>
                        <Col>
                            <h1>Welcome to Our Dictionary</h1>
                            <p>
                                Our dictionary provides comprehensive translations between French and Romanian words.
                                Whether you're learning a new language or need to translate text, our dictionary is
                                here to help you.
                            </p>
                        </Col>
                    </Row>
                    <Row>
                        <Col md={{ size: 6, offset: 3 }}>
                            <h3>Login</h3>
                            <Form>
                                <FormGroup>
                                    <Label for="username">Username</Label>
                                    <Input type="text" name="username" id="username" placeholder="Enter your username" />
                                </FormGroup>
                                <FormGroup>
                                    <Label for="password">Password</Label>
                                    <Input type="password" name="password" id="password" placeholder="Enter your password" />
                                </FormGroup>
                                <Button color="primary" onClick={this.handleLogin}>Login</Button>
                            </Form>
                        </Col>
                    </Row>
                    <Row>
                        <Col md={{ size: 6, offset: 3 }}>
                            <h3>Register</h3>
                            {/* Registration form */}
                            {/* Similar to login form, capture input values and handle registration */}
                            {/* You can implement this part similarly to how the login form is implemented */}
                        </Col>
                    </Row>
                </Container>
            </div>
        );
    }
}

export default withRouter(Home);
