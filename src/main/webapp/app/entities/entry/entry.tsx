import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './entry.reducer';
import { IEntry } from 'app/shared/model/entry.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEntryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Entry extends React.Component<IEntryProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { entryList, match } = this.props;
    return (
      <div>
        <h2 id="entry-heading">
          Entries
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Entry
          </Link>
        </h2>
        <div className="table-responsive">
          {entryList && entryList.length > 0 ? (
            <Table responsive aria-describedby="entry-heading">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Date</th>
                  <th>Public Id</th>
                  <th>Bar</th>
                  <th>App User</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {entryList.map((entry, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${entry.id}`} color="link" size="sm">
                        {entry.id}
                      </Button>
                    </td>
                    <td>
                      <TextFormat type="date" value={entry.date} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{entry.publicId}</td>
                    <td>{entry.barId ? <Link to={`animal-bar/${entry.barId}`}>{entry.barId}</Link> : ''}</td>
                    <td>{entry.appUserId ? <Link to={`app-user/${entry.appUserId}`}>{entry.appUserId}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${entry.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${entry.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${entry.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Entries found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ entry }: IRootState) => ({
  entryList: entry.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Entry);
