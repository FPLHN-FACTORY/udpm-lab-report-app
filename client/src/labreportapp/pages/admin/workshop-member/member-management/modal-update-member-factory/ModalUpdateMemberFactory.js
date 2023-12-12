import { Modal, Row, Input, Button, message, Col, Radio, Select } from "antd";
import "react-toastify/dist/ReactToastify.css";
import "./style-modal-update-member-factory.css";
import { AdMemberFactoryAPI } from "../../../../../api/admin/AdMemberFactoryAPI";
import { useEffect, useState } from "react";
import LogoTeamFactory from "../../../../../assets/img/logo_team_factory.png";
import LoadingIndicatorNoOverlay from "../../../../../helper/loadingNoOverlay";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit } from "@fortawesome/free-solid-svg-icons";
import { useAppDispatch } from "../../../../../app/hook";
import { UpdateAdMemberFactory } from "../../../../../app/admin/AdMemberFactorySlice.reducer";

const ModalUpdateMemberFactory = ({ visible, onCancel, id, roles, teams }) => {
  const [detailMemberFactory, setDetailMemberFactory] = useState(null);
  const [loading, setLoading] = useState(false);
  const [status, setStatus] = useState("");
  const [teamsDetail, setTeamsDetail] = useState([]);
  const [rolesDetail, setRolesDetail] = useState([]);

  useEffect(() => {
    if (id != null) {
      loadDataDetail();
    }
    return () => {
      setDetailMemberFactory(null);
      setStatus("");
      setTeamsDetail([]);
      setRolesDetail([]);
    };
  }, [id, visible]);

  const loadDataDetail = () => {
    setLoading(true);
    AdMemberFactoryAPI.detailMemberFactory(id).then(
      (response) => {
        setDetailMemberFactory(response.data.data);
        setStatus(response.data.data.statusMemberFactory + "");
        setTeamsDetail(response.data.data.teams);
        setRolesDetail(response.data.data.roles);
        setLoading(false);
      },
      (error) => {}
    );
  };
  const dispatch = useAppDispatch();
  const update = () => {
    setLoading(true);
    let obj = {
      id: id,
      teams: teamsDetail,
      roles: rolesDetail,
      status: parseInt(status),
    };

    AdMemberFactoryAPI.updateMemberFactory(obj).then((response) => {
      dispatch(UpdateAdMemberFactory(response.data.data));
      message.success("Cập nhật thành công !");
      setLoading(false);
      onCancel();
    });
  };

  return (
    <>
      {loading && <LoadingIndicatorNoOverlay />}
      <Modal
        open={visible}
        onCancel={onCancel}
        width={750}
        footer={null}
        title="Cập nhật thông tin thành viên"
        className="modal_show_detail_create_level"
      >
        <div style={{ marginTop: 25, paddingBottom: 10 }}>
          <Row>
            <Col span={4} style={{ display: "flex", justifyContent: "center" }}>
              <img
                width="100%"
                style={{
                  borderRadius: 5,
                  border: "1px solid rgb(216, 216, 216)",
                }}
                src={
                  detailMemberFactory != null &&
                  detailMemberFactory.picture !== "/image/Default.png"
                    ? detailMemberFactory.picture
                    : LogoTeamFactory
                }
              />
            </Col>
            <Col span={20} style={{ paddingLeft: 15 }}>
              <div>
                Họ và tên:
                <Input
                  type="text"
                  readOnly={true}
                  value={
                    detailMemberFactory != null ? detailMemberFactory.name : ""
                  }
                />{" "}
                <br />
              </div>
              <div style={{ marginTop: 10 }}>
                Email:
                <Input
                  type="text"
                  readOnly={true}
                  value={
                    detailMemberFactory != null ? detailMemberFactory.email : ""
                  }
                />{" "}
                <br />
              </div>
            </Col>
          </Row>
          <Row style={{ marginTop: 15 }}>
            Nhóm đang tham gia:
            <Select
              mode="multiple"
              style={{ width: "100%" }}
              value={teamsDetail}
              onChange={(e) => {
                setTeamsDetail(e);
              }}
              placeholder="Chưa có nhóm"
              showSearch
              filterOption={(input, option) =>
                option.props.children
                  .toLowerCase()
                  .indexOf(input.toLowerCase()) >= 0
              }
            >
              {teams.map((item) => {
                return (
                  <Select.Option value={item.id}>{item.name}</Select.Option>
                );
              })}
            </Select>
          </Row>
          <Row style={{ marginTop: 15 }}>
            Vai trò:
            <Select
              placeholder="Chưa có vai trò"
              mode="multiple"
              style={{ width: "100%" }}
              value={rolesDetail}
              onChange={(e) => {
                setRolesDetail(e);
              }}
              showSearch
              filterOption={(input, option) =>
                option.props.children
                  .toLowerCase()
                  .indexOf(input.toLowerCase()) >= 0
              }
            >
              {roles.map((item) => {
                return (
                  <Select.Option value={item.id}>{item.name}</Select.Option>
                );
              })}
            </Select>
          </Row>
          <Row
            style={{
              marginTop: 20,
              display: "flex",
              justifyContent: "center",
            }}
          >
            <div style={{ marginRight: 10, marginTop: 5 }}>Trạng thái:</div>
            <Radio.Group
              value={status}
              onChange={(e) => {
                setStatus(e.target.value);
              }}
            >
              <Radio.Button value="0">Hoạt động</Radio.Button>
              <Radio.Button value="1">Không hoạt động</Radio.Button>
            </Radio.Group>
          </Row>
        </div>
        <Row style={{ display: "flex", justifyContent: "center" }}>
          <Button
            style={{
              backgroundColor: "rgb(38, 144, 214)",
              color: "white",
            }}
            onClick={update}
          >
            <FontAwesomeIcon icon={faEdit} style={{ marginRight: 5 }} /> Cập
            nhật
          </Button>
        </Row>
      </Modal>
    </>
  );
};

export default ModalUpdateMemberFactory;
