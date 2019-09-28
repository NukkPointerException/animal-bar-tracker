import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAnimalBar } from 'app/shared/model/animal-bar.model';
import { getEntities as getAnimalBars } from 'app/entities/animal-bar/animal-bar.reducer';
import { IAppUser } from 'app/shared/model/app-user.model';
import { getEntities as getAppUsers } from 'app/entities/app-user/app-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './entry.reducer';
import { IEntry } from 'app/shared/model/entry.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEntryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEntryUpdateState {
  isNew: boolean;
  barId: string;
  appUserId: string;
}

export class EntryUpdate extends React.Component<IEntryUpdateProps, IEntryUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      barId: '0',
      appUserId: '0',
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

    this.props.getAnimalBars();
    this.props.getAppUsers();
  }

  saveEntity = (event, errors, values) => {
    values.date = convertDateTimeToServer(values.date);

    if (errors.length === 0) {
      const { entryEntity } = this.props;
      const entity = {
        ...entryEntity,
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
    this.props.history.push('/entity/entry');
  };

  render() {
    const { entryEntity, animalBars, appUsers, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="animalBarTrackerApp.entry.home.createOrEditLabel">Create or edit a Entry</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : entryEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="entry-id">ID</Label>
                    <AvInput id="entry-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="dateLabel" for="entry-date">
                    Date
                  </Label>
                  <AvInput
                    id="entry-date"
                    type="datetime-local"
                    className="form-control"
                    name="date"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.entryEntity.date)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="publicIdLabel" for="entry-publicId">
                    Public Id
                  </Label>
                  <AvField id="entry-publicId" type="text" name="publicId" />
                </AvGroup>
                <AvGroup>
                  <Label for="entry-bar">Bar</Label>
                  <AvInput id="entry-bar" type="select" className="form-control" name="barId">
                    <option value="" key="0" />
                    {animalBars
                      ? animalBars.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="entry-appUser">App User</Label>
                  <AvInput id="entry-appUser" type="select" className="form-control" name="appUserId">
                    <option value="" key="0" />
                    {appUsers
                      ? appUsers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/entry" replace color="info">
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
  animalBars: storeState.animalBar.entities,
  appUsers: storeState.appUser.entities,
  entryEntity: storeState.entry.entity,
  loading: storeState.entry.loading,
  updating: storeState.entry.updating,
  updateSuccess: storeState.entry.updateSuccess
});

const mapDispatchToProps = {
  getAnimalBars,
  getAppUsers,
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
)(EntryUpdate);
