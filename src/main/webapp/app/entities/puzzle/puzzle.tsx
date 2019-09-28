import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './puzzle.reducer';
import { IPuzzle } from 'app/shared/model/puzzle.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPuzzleProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Puzzle extends React.Component<IPuzzleProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { puzzleList, match } = this.props;
    return (
      <div>
        <h2 id="puzzle-heading">
          Puzzles
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Puzzle
          </Link>
        </h2>
        <div className="table-responsive">
          {puzzleList && puzzleList.length > 0 ? (
            <Table responsive aria-describedby="puzzle-heading">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Type</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {puzzleList.map((puzzle, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${puzzle.id}`} color="link" size="sm">
                        {puzzle.id}
                      </Button>
                    </td>
                    <td>{puzzle.type}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${puzzle.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${puzzle.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${puzzle.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Puzzles found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ puzzle }: IRootState) => ({
  puzzleList: puzzle.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Puzzle);
