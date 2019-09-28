import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './app-user.reducer';
import { IAppUser } from 'app/shared/model/app-user.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAppUserUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAppUserUpdateState {
  isNew: boolean;
}

export class AppUserUpdate extends React.Component<IAppUserUpdateProps, IAppUserUpdateState> {
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
      const { appUserEntity } = this.props;
      const entity = {
        ...appUserEntity,
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
    this.props.history.push('/entity/app-user');
  };

  render() {
    const { appUserEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="animalBarTrackerApp.appUser.home.createOrEditLabel">Create or edit a AppUser</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : appUserEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="app-user-id">ID</Label>
                    <AvInput id="app-user-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="userNameLabel" for="app-user-userName">
                    User Name
                  </Label>
                  <AvField id="app-user-userName" type="text" name="userName" />
                </AvGroup>
                <AvGroup>
                  <Label id="firstNameLabel" for="app-user-firstName">
                    First Name
                  </Label>
                  <AvField id="app-user-firstName" type="text" name="firstName" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastNameLabel" for="app-user-lastName">
                    Last Name
                  </Label>
                  <AvField id="app-user-lastName" type="text" name="lastName" />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="app-user-email">
                    Email
                  </Label>
                  <AvField id="app-user-email" type="text" name="email" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/app-user" replace color="info">
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
  appUserEntity: storeState.appUser.entity,
  loading: storeState.appUser.loading,
  updating: storeState.appUser.updating,
  updateSuccess: storeState.appUser.updateSuccess
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
)(AppUserUpdate);
