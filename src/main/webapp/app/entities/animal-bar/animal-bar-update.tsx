import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IWrapper } from 'app/shared/model/wrapper.model';
import { getEntities as getWrappers } from 'app/entities/wrapper/wrapper.reducer';
import { IPuzzle } from 'app/shared/model/puzzle.model';
import { getEntities as getPuzzles } from 'app/entities/puzzle/puzzle.reducer';
import { IChocolate } from 'app/shared/model/chocolate.model';
import { getEntities as getChocolates } from 'app/entities/chocolate/chocolate.reducer';
import { getEntity, updateEntity, createEntity, reset } from './animal-bar.reducer';
import { IAnimalBar } from 'app/shared/model/animal-bar.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAnimalBarUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAnimalBarUpdateState {
  isNew: boolean;
  wrapperId: string;
  puzzleId: string;
  chocolateId: string;
}

export class AnimalBarUpdate extends React.Component<IAnimalBarUpdateProps, IAnimalBarUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      wrapperId: '0',
      puzzleId: '0',
      chocolateId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getWrappers();
    this.props.getPuzzles();
    this.props.getChocolates();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { animalBarEntity } = this.props;
      const entity = {
        ...animalBarEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/animal-bar');
  };

  render() {
    const { animalBarEntity, wrappers, puzzles, chocolates, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="animalBarTrackerApp.animalBar.home.createOrEditLabel">Create or edit a AnimalBar</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : animalBarEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="animal-bar-id">ID</Label>
                    <AvInput id="animal-bar-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label for="animal-bar-wrapper">Wrapper</Label>
                  <AvInput id="animal-bar-wrapper" type="select" className="form-control" name="wrapperId">
                    <option value="" key="0" />
                    {wrappers
                      ? wrappers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="animal-bar-puzzle">Puzzle</Label>
                  <AvInput id="animal-bar-puzzle" type="select" className="form-control" name="puzzleId">
                    <option value="" key="0" />
                    {puzzles
                      ? puzzles.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="animal-bar-chocolate">Chocolate</Label>
                  <AvInput id="animal-bar-chocolate" type="select" className="form-control" name="chocolateId">
                    <option value="" key="0" />
                    {chocolates
                      ? chocolates.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/animal-bar" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  wrappers: storeState.wrapper.entities,
  puzzles: storeState.puzzle.entities,
  chocolates: storeState.chocolate.entities,
  animalBarEntity: storeState.animalBar.entity,
  loading: storeState.animalBar.loading,
  updating: storeState.animalBar.updating,
  updateSuccess: storeState.animalBar.updateSuccess
});

const mapDispatchToProps = {
  getWrappers,
  getPuzzles,
  getChocolates,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AnimalBarUpdate);
