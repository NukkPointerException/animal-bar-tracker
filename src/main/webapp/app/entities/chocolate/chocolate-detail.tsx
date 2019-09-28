import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './chocolate.reducer';
import { IChocolate } from 'app/shared/model/chocolate.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IChocolateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ChocolateDetail extends React.Component<IChocolateDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { chocolateEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Chocolate [<b>{chocolateEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="leftImage">Left Image</span>
            </dt>
            <dd>{chocolateEntity.leftImage}</dd>
            <dt>
              <span id="rightImage">Right Image</span>
            </dt>
            <dd>{chocolateEntity.rightImage}</dd>
          </dl>
          <Button tag={Link} to="/entity/chocolate" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/chocolate/${chocolateEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ chocolate }: IRootState) => ({
  chocolateEntity: chocolate.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ChocolateDetail);
