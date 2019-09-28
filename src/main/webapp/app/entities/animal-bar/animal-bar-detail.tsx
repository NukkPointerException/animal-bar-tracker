import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './animal-bar.reducer';
import { IAnimalBar } from 'app/shared/model/animal-bar.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAnimalBarDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AnimalBarDetail extends React.Component<IAnimalBarDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { animalBarEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            AnimalBar [<b>{animalBarEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>Wrapper</dt>
            <dd>{animalBarEntity.wrapperId ? animalBarEntity.wrapperId : ''}</dd>
            <dt>Puzzle</dt>
            <dd>{animalBarEntity.puzzleId ? animalBarEntity.puzzleId : ''}</dd>
            <dt>Chocolate</dt>
            <dd>{animalBarEntity.chocolateId ? animalBarEntity.chocolateId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/animal-bar" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/animal-bar/${animalBarEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ animalBar }: IRootState) => ({
  animalBarEntity: animalBar.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AnimalBarDetail);
