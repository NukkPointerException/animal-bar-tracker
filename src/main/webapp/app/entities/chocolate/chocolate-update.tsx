import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './chocolate.reducer';
import { IChocolate } from 'app/shared/model/chocolate.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IChocolateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IChocolateUpdateState {
  isNew: boolean;
}

export class ChocolateUpdate extends React.Component<IChocolateUpdateProps, IChocolateUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { chocolateEntity } = this.props;
      const entity = {
        ...chocolateEntity,
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
    this.props.history.push('/entity/chocolate');
  };

  render() {
    const { chocolateEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="animalBarTrackerApp.chocolate.home.createOrEditLabel">Create or edit a Chocolate</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : chocolateEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="chocolate-id">ID</Label>
                    <AvInput id="chocolate-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="leftImageLabel" for="chocolate-leftImage">
                    Left Image
                  </Label>
                  <AvInput
                    id="chocolate-leftImage"
                    type="select"
                    className="form-control"
                    name="leftImage"
                    value={(!isNew && chocolateEntity.leftImage) || 'LION'}
                  >
                    <option value="LION">LION</option>
                    <option value="ZEBRA">ZEBRA</option>
                    <option value="GAZELLE">GAZELLE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="rightImageLabel" for="chocolate-rightImage">
                    Right Image
                  </Label>
                  <AvInput
                    id="chocolate-rightImage"
                    type="select"
                    className="form-control"
                    name="rightImage"
                    value={(!isNew && chocolateEntity.rightImage) || 'LION'}
                  >
                    <option value="LION">LION</option>
                    <option value="ZEBRA">ZEBRA</option>
                    <option value="GAZELLE">GAZELLE</option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/chocolate" replace color="info">
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
  chocolateEntity: storeState.chocolate.entity,
  loading: storeState.chocolate.loading,
  updating: storeState.chocolate.updating,
  updateSuccess: storeState.chocolate.updateSuccess
});

const mapDispatchToProps = {
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
)(ChocolateUpdate);
