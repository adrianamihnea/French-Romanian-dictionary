import React, { Component } from 'react';
import { Container, Row, Col, Form, FormGroup, Label, Input, Button } from 'reactstrap';
import { withRouter } from 'react-router-dom';

class Home extends Component {
    handleLogin = () => {
        // Redirect to the list of words
        this.props.history.push('/words');
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
                </Container>
            </div>
        );
    }
}

export default withRouter(Home);
