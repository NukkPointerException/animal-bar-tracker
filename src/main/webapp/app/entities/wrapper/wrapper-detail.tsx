import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './wrapper.reducer';
import { IWrapper } from 'app/shared/model/wrapper.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWrapperDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class WrapperDetail extends React.Component<IWrapperDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { wrapperEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Wrapper [<b>{wrapperEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="picture">Picture</span>
            </dt>
            <dd>{wrapperEntity.picture}</dd>
          </dl>
          <Button tag={Link} to="/entity/wrapper" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/wrapper/${wrapperEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ wrapper }: IRootState) => ({
  wrapperEntity: wrapper.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WrapperDetail);
