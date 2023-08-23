import { Modal, Row, Col, Input, Button } from "antd";
import { useEffect, useState } from "react";
import { AdSemesterAPI } from "../../../../api/admin/AdSemesterAPI";
import moment from "moment";
import { useAppSelector } from "../../../../app/hook";
import { AddSemester } from "../../../../app/admin/AdSemester.reducer";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch } from "../../../../app/hook";

const ModalCreateSemester = ({ visible, onCancel }) => {
  const [name, setName] = useState("");
  const [errorName, setErrorName] = useState("");
  const [startTime, setStartTime] = useState("");
  const [errorStartTime, setErrorStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [errorEndTime, setErrorEndTime] = useState("");
  const dispatch = useAppDispatch();

  useEffect(() => {
    return () => {
      setName("");
      setStartTime("");
      setEndTime("");
      setErrorEndTime("");
      setErrorStartTime("");
      setErrorName();
    };
  }, [visible]);

  const create = () => {
    let check = 0;
    if (name.trim() === "") {
      setErrorName("Tên học kỳ không được để trống");
      check++;
    } else {
      setErrorName("");
    }
    if (name.trim().length > 500) {
      setErrorName("Tên học kỳ không quá 500 ký tự");
      check++;
    } else {
      setErrorName("");
    }
    if (startTime === "") {
      setErrorStartTime("Thời gian bắt đầu không được để trống");
      check++;
    } else {
      setErrorStartTime("");
    }
    if (endTime === "") {
      setErrorEndTime("Thời gian kết thúc không được để trống");
      check++;
    } else {
      setErrorEndTime("");
    }
    if (new Date(startTime) > new Date(endTime)) {
      setErrorStartTime(
        "Thời gian bắt đầu không được lớn hơn thời gian kết thúc"
      );
      check++;
    } else {
      if (startTime === "") {
        setErrorStartTime("Thời gian bắt đầu không được để trống");
        check++;
      } else {
        setErrorStartTime("");
      }
    }

    if (check === 0) {
      let obj = {
        name: name,
        startTime: moment(startTime, "YYYY-MM-DD").valueOf(),
        endTime: moment(endTime, "YYYY-MM-DD").valueOf(),
      };

      AdSemesterAPI.addSemester(obj).then(
        (response) => {
          toast.success("Thêm thành công!");
          dispatch(AddSemester(response.data.data));
          onCancel();
        },
        (error) => {
          toast.error(error.response.data.message);
        }
      );
    }
  };

  return (
    <>
      <Modal
        visible={visible}
        onCancel={onCancel}
        width={750}
        footer={null}
        className="modal_show_detail"
      >
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Thêm mới học kỳ</span>
        </div>
        <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span>Tên học kỳ:</span> <br />
              <Input
                value={name}
                onChange={(e) => {
                  setName(e.target.value);
                }}
                type="text"
              />
              <span className="error">{errorName}</span>
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={12}>
              <span>Thời gian bắt đầu:</span> <br />
              <Input
                value={startTime}
                onChange={(e) => {
                  setStartTime(e.target.value);
                }}
                type="date"
              />
              <span className="error">{errorStartTime}</span>
            </Col>
            <Col span={12}>
              <span>Thời gian kết thúc:</span> <br />
              <Input
                value={endTime}
                onChange={(e) => {
                  setEndTime(e.target.value);
                }}
                type="date"
              />
              <span className="error">{errorEndTime}</span>
            </Col>
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
              onClick={create}
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

export default ModalCreateSemester;