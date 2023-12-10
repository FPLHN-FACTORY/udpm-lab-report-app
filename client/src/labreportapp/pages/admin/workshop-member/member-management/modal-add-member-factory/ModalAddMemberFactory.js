import { Modal, Row, Input, Button, message } from "antd";
import "react-toastify/dist/ReactToastify.css";
import "./style-modal-add-member-factory.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMailBulk } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { AdMemberFactoryAPI } from "../../../../../api/admin/AdMemberFactoryAPI";
import { useAppDispatch } from "../../../../../app/hook";
import { AddAdMemberFactory } from "../../../../../app/admin/AdMemberFactorySlice.reducer";
import LoadingIndicatorNoOverlay from "../../../../../helper/loadingNoOverlay";

const ModalAddMemberFactory = ({
  visible,
  onCancel,
  setNumberMemberFactory,
  numberMemberFactory,
}) => {
  const [email, setEmail] = useState("");
  const [errorEmail, setErrorEmail] = useState("");
  const [loading, setLoading] = useState(false);
  const dispatch = useAppDispatch();

  useEffect(() => {
    return () => {
      setErrorEmail("");
      setEmail("");
    };
  }, [visible]);

  const handleAddMemberFactory = () => {
    let check = 0;
    if (email.trim() === "") {
      setErrorEmail("Vui lòng nhập email");
      ++check;
    } else {
      setErrorEmail("");
    }
    if (check === 0) {
      setLoading(true);
      AdMemberFactoryAPI.addMemberFactory(email).then(
        (res) => {
          dispatch(AddAdMemberFactory(res.data.data));
          message.success("Thêm thành công !");
          setLoading(false);
          setNumberMemberFactory(numberMemberFactory + 1);
          onCancel();
        },
        (error) => {}
      );
    }
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
        <div style={{ marginTop: 30, textAlign: "center" }}>
          <FontAwesomeIcon icon={faMailBulk} style={{ fontSize: 25 }} /> <br />
          <span style={{ fontSize: 20, fontWeight: 500 }}>
            Mời thành viên tham gia xưởng dự án bộ môn UDPM
          </span>
        </div>
        <Row
          style={{
            marginTop: 30,
            display: "flex",
            alignItems: "center",
            paddingBottom: 10,
          }}
        >
          Nhập email của thành viên: <br />
          <Input
            type="email"
            placeholder="🔍 Nhập email..."
            style={{ width: "90%" }}
            value={email}
            onChange={(e) => {
              setEmail(e.target.value);
            }}
          />
          <Button
            style={{
              backgroundColor: "rgb(38, 144, 214)",
              color: "white",
            }}
            onClick={handleAddMemberFactory}
          >
            Thêm
          </Button>
        </Row>
        <span style={{ color: "red" }}>{errorEmail}</span>
      </Modal>
    </>
  );
};

export default ModalAddMemberFactory;
