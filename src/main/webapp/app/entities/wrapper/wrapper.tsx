import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './wrapper.reducer';
import { IWrapper } from 'app/shared/model/wrapper.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWrapperProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Wrapper extends React.Component<IWrapperProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { wrapperList, match } = this.props;
    return (
      <div>
        <h2 id="wrapper-heading">
          Wrappers
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Wrapper
          </Link>
        </h2>
        <div className="table-responsive">
          {wrapperList && wrapperList.length > 0 ? (
            <Table responsive aria-describedby="wrapper-heading">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Picture</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {wrapperList.map((wrapper, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${wrapper.id}`} color="link" size="sm">
                        {wrapper.id}
                      </Button>
                    </td>
                    <td>{wrapper.picture}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${wrapper.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${wrapper.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${wrapper.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Wrappers found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ wrapper }: IRootState) => ({
  wrapperList: wrapper.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Wrapper);
