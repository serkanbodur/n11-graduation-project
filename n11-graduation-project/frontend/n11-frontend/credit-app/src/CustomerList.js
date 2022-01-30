import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from './AppNavbar';
import {Link} from 'react-router-dom';
import {Modal} from 'antd';
import {Footer} from "antd/es/layout/layout";

class CustomerList extends Component {
    state = {deleteModalVisible: false};

    constructor(props) {
        super(props);
        this.state = {customers: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/api/v1/customers')
            .then(response => response.json())
            .then(data => this.setState({customers: data}));
    }


    async remove(id) {
        await fetch(`/api/v1/customers/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedCustomers = [...this.state.customers].filter(i => i.id !== id);
            this.setState({customers: updatedCustomers});
        });
    }

    showDeleteModal = () => {
        this.setState({
            deleteModalVisible: true,
        });
    };

    hideDeleteModal = () => {
        this.setState({
            deleteModalVisible: false,
        });
    };

    render() {
        const {customers, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const customerList = customers.map(customer => {
            console.log(customer)
            return <tr key={customer.id}>
                <td>{customer.idNumber}</td>
                <td style={{whiteSpace: 'nowrap'}}>{customer.name}</td>
                <td>{customer.surname}</td>
                <td>{customer.phone}</td>
                <td>{customer.dateOfBirth}</td>
                <td>{customer.monthlyIncome}</td>
                <td>{customer.guaranteeFee}</td>
                <td>
                    <ButtonGroup>
                        <Link to={`/customers/${customer.id}`}><Button color="primary">Edit</Button></Link>
                        <Button size="sm" color="danger" onClick={() => this.remove(customer.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (

            <div className="App">
                <AppNavbar defaultSelected="1"/>

                <Container className="App" fluid style={{minHeight: 1000, padding: 50}}>
                    <div>
                        <Button style={{textAlign: "left"}} className="" color="success" tag={Link}
                                to="/customers/new">Add Customer</Button>
                        <h3 style={{textAlign: "center"}}>Customers</h3>
                    </div>

                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="10%">TR ID Number</th>
                            <th width="20%">Name</th>
                            <th width="20%">Surname</th>
                            <th width="10%">Phone</th>
                            <th width="10%">Birth Date</th>
                            <th width="10%">Monthly Income</th>
                            <th width="10%">Gurantee Fee</th>
                        </tr>
                        </thead>
                        <tbody>
                        {customerList}
                        </tbody>
                    </Table>
                </Container>
                <Footer style={{textAlign: 'center'}}>Ant Design Â©2018 Created by Ant UED</Footer>
            </div>

        );
    }
}

export default CustomerList;