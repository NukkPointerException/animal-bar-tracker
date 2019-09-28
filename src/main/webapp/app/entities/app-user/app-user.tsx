import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './app-user.reducer';
import { IAppUser } from 'app/shared/model/app-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAppUserProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class AppUser extends React.Component<IAppUserProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { appUserList, match } = this.props;
    return (
      <div>
        <h2 id="app-user-heading">
          App Users
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new App User
          </Link>
        </h2>
        <div className="table-responsive">
          {appUserList && appUserList.length > 0 ? (
            <Table responsive aria-describedby="app-user-heading">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>User Name</th>
                  <th>First Name</th>
                  <th>Last Name</th>
                  <th>Email</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {appUserList.map((appUser, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${appUser.id}`} color="link" size="sm">
                        {appUser.id}
                      </Button>
                    </td>
                    <td>{appUser.userName}</td>
                    <td>{appUser.firstName}</td>
                    <td>{appUser.lastName}</td>
                    <td>{appUser.email}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${appUser.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${appUser.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${appUser.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No App Users found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ appUser }: IRootState) => ({
  appUserList: appUser.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AppUser);
