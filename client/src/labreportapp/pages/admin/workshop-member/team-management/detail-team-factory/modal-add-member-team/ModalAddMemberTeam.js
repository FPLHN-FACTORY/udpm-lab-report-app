import { Modal, Row, Col, Input, Button, Select, message } from "antd";
import Image from "../../../../../../../portalprojects/helper/img/Image";
import { useEffect, useState } from "react";
import { AdTeamAPI } from "../../../../../../api/admin/AdTeamAPI";
import { useParams } from "react-router";
import LoadingIndicatorNoOverlay from "../../../../../../helper/loadingNoOverlay";

const { Option } = Select;

const ModalAddMemberTeam = ({
  visible,
  onCancel,
  allMemberFactory,
  fetchAll,
  fetchAllMemberFactory,
}) => {
  const [allMember, setAllMember] = useState([]);
  const { id } = useParams();
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setAllMember(allMemberFactory);
    setSelecteds([]);
  }, [visible]);

  const [selecteds, setSelecteds] = useState([]);

  const handleAddMember = () => {
    if (selecteds.length === 0) {
      message.error("Hãy chọn thành viên cần thêm vào nhóm");
      return;
    }
    let obj = { idTeam: id, listMemberId: selecteds };
    setLoading(true);
    AdTeamAPI.addMembers(obj).then(
      (response) => {
        message.success("Thêm thành công");
        fetchAll();
        fetchAllMemberFactory();
        setLoading(false);
        onCancel();
      },
      (error) => {}
    );
  };
  const handleChange = (selectedValues) => {
    setSelecteds(selectedValues);
  };

  return (
    <>
      {loading && <LoadingIndicatorNoOverlay />}
      <Modal
        visible={visible}
        onCancel={onCancel}
        width={750}
        footer={null}
        className="modal_show_detail_create_level"
      >
        <div style={{ paddingTop: "0" }}>
          <span style={{ fontSize: "18px" }}>Thêm thành viên vào nhóm</span>
        </div>

        <div style={{ marginTop: 20, marginBottom: 20 }}>
          Danh sách thành viên chưa tham gia nhóm:
          <Row>
            {" "}
            <Select
              value={selecteds}
              mode="multiple"
              style={{ width: "100%" }}
              placeholder="Chọn một hoặc nhiều thành viên"
              onChange={handleChange}
            >
              {allMember.map((item) => (
                <Option value={item.id} key={item.id}>
                  <div style={{ display: "flex", alignItems: "center" }}>
                    <Image url={item.picture} picxel={25} />{" "}
                    <span style={{ marginLeft: 10 }}>
                      {" "}
                      {item.name + " (" + item.email + ") "}
                    </span>
                  </div>
                </Option>
              ))}
            </Select>
          </Row>
        </div>
        <div style={{ textAlign: "right" }}>
          <div style={{ paddingTop: "15px" }}>
            <Button
              style={{
                marginRight: "5px",
                backgroundColor: "rgb(61, 139, 227)",
                color: "white",
              }}
              onClick={handleAddMember}
            >
              Thêm
            </Button>
            <Button
              style={{
                backgroundColor: "red",
                color: "white",
              }}
              onClick={onCancel}
            >
              Hủy
            </Button>
          </div>
        </div>
      </Modal>
    </>
  );
};

export default ModalAddMemberTeam;
