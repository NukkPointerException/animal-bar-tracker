import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './app-user.reducer';
import { IAppUser } from 'app/shared/model/app-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAppUserDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AppUserDetail extends React.Component<IAppUserDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { appUserEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            AppUser [<b>{appUserEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="userName">User Name</span>
            </dt>
            <dd>{appUserEntity.userName}</dd>
            <dt>
              <span id="firstName">First Name</span>
            </dt>
            <dd>{appUserEntity.firstName}</dd>
            <dt>
              <span id="lastName">Last Name</span>
            </dt>
            <dd>{appUserEntity.lastName}</dd>
            <dt>
              <span id="email">Email</span>
            </dt>
            <dd>{appUserEntity.email}</dd>
          </dl>
          <Button tag={Link} to="/entity/app-user" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/app-user/${appUserEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ appUser }: IRootState) => ({
  appUserEntity: appUser.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AppUserDetail);
