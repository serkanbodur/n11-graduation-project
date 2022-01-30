import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from './AppNavbar';
import {Card, Row} from 'antd';

class CustomerEdit extends Component {

    emptyItem = {
        name: '',
        surname: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async getUser(id) {

    }


    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const customer = await (await fetch(`/api/v1/customers/${this.props.match.params.id}`)).json();
            this.setState({item: customer});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
        await fetch('/api/v1/customers' + (item.id ? '/' + item.id : ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/customers');
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Customer' : 'Add Customer'}</h2>;

        return <div className="App">
            <AppNavbar defaultSelected ="1"/>
            <Container>
                <Row type="flex" justify="center" align="middle" style={{minHeight: '100vh'}}>
                    <Card title={title} bordered={false}
                          style={{backgroundColor: "#f5f7fa", borderRadius: 30, width: 600}}>
                        <Form onSubmit={this.handleSubmit} style={{textAlign:"left"}}>
                            <FormGroup>
                                <Label for="idNumber">TR ID Number</Label>
                                <Input type="number" name="idNumber" id="idNumber" value={item.idNumber || ''}
                                       onChange={this.handleChange} autoComplete="idNumber"/>
                            </FormGroup>
                            <FormGroup>
                                <Label for="name">Name</Label>
                                <Input type="text" name="name" id="name" value={item.name || ''}
                                       onChange={this.handleChange} autoComplete="name"/>
                            </FormGroup>
                            <FormGroup>
                                <Label for="surname">Surname</Label>
                                <Input type="text" name="surname" id="surname" value={item.surname || ''}
                                       onChange={this.handleChange} autoComplete="surname"/>
                            </FormGroup>
                            <FormGroup>
                                <Label for="phone">Phone</Label>
                                <Input type="text" name="phone" id="phone" value={item.phone || ''}
                                       onChange={this.handleChange} autoComplete="phone"/>
                            </FormGroup>
                            <FormGroup>
                                <Label for="dateOfBirth">Birth Date</Label>
                                <Input type="text" name="dateOfBirth" id="dateOfBirth" value={item.dateOfBirth || ''}
                                       onChange={this.handleChange} autoComplete="dateOfBirth"/>
                            </FormGroup>
                            <FormGroup>
                                <Label for="monthlyIncome">Monthly Income</Label>
                                <Input type="number" name="monthlyIncome" id="monthlyIncome"
                                       value={item.monthlyIncome || ''}
                                       onChange={this.handleChange} autoComplete="monthlyIncome"/>
                            </FormGroup>
                            <FormGroup>
                                <Label for="guaranteeFee">Guarantee Fee</Label>
                                <Input type="number" name="guaranteeFee" id="guaranteeFee" value={item.guaranteeFee || ''}
                                       onChange={this.handleChange} autoComplete="guaranteeFee"/>
                            </FormGroup>
                            <FormGroup style={{ textAlign: "center",marginTop:20}}>
                                <Button color="primary" type="submit">Save</Button>{' '}
                                <Button color="secondary" tag={Link} to="/customers">Cancel</Button>
                            </FormGroup>
                        </Form>
                    </Card>
                </Row>
            </Container>
        </div>
    }

}

export default withRouter(CustomerEdit);