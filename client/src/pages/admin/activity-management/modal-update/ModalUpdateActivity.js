import "./styleModalUpdateActivity.css";
import { Modal, Row, Col, Input, Button, DatePicker, Select } from "antd";
import { useEffect, useState } from "react";
import { useAppDispatch } from "../../../../app/hook";
import { toast } from "react-toastify";
import { ActivityManagementAPI } from "../../../../api/admin/activity-management/activityManagement.api";
import { UpdateActivityManagement } from "../../../../app/admin/activity-management/activityManagementSlice.reducer";
import { Option } from "antd/es/mentions";

const ModalUpdateActivity = ({visible, onCancel}) => {
    const [name, setName] = useState("");
    const [startTime, setStartTime] = useState("");
    const [endTime, setEndTime] = useState("");
    const [level, setLevel] = useState("");
    const [semesterId, setSemesterId] = useState("");
    const [errorName, setErrorName] = useState("");
    const [errorStartTime, setErrorStartTime] = useState("");
    const [errorEndTime, setErrorEndTime] = useState("");
    const [errorLevel, setErrorLevel] = useState("");
    const [errorSemesterId, setErrorSemesterId] = useState("");
    const dispatch = useAppDispatch();

    useEffect(() => {
        if (visible === true) {
            return () => {
                setName("");
                setStartTime("");
                setEndTime("");
                setLevel("");
                setErrorName("");
                setErrorStartTime("");
                setErrorEndTime("");
                setErrorLevel("");
            };
        }
    }, [visible]);

    const update = () => {
        let check = 0;

        if (name.trim() === "") {
            setErrorName("Tên không được để trống");
            check++;
        } else {
            setErrorName("");
        }
        if (startTime === "") {
            setErrorStartTime("Hãy chọn thời gian bắt đầu");
            check++;
        } else {
            setErrorStartTime("");
        }
        if (endTime === "") {
            setErrorEndTime("Hãy chọn thời gian kết thúc");
            check++;
        } else {
            setErrorEndTime("");
        }
        if (level === "") {
            setErrorLevel("Hãy chọn cấp độ");
            check++;
        } else {
            setErrorLevel("");
        }
        if (check === 0) {
            let obj = {
                name: name,
                startTime: startTime,
                endTime: endTime,
                level: level,
                semesterId: semesterId,
            };
            ActivityManagementAPI.update(obj).then(
                (response) => {
                    toast.success("Thêm thành công!");
                    dispatch(UpdateActivityManagement(response.data.data));
                    onCancel();
                },
                (error) => {
                    toast.error(error.response.data.message);
                }
            );
        }
    }

    return (
        <Modal
            visible={visible}
            onCancel={onCancel}
            width={750}
            footer={null}
            className="modal_show_detail"
        >
            {" "}
            <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
                <span style={{ fontSize: "18px" }}>Thêm hoạt động</span>
            </div>
            <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
                <Row gutter={16} style={{ marginTop: "15px" }}>
                    <Col span={24}>
                        <span>Tên:</span> <br />
                        <Input
                            value={name}
                            onChange={(e) => {
                                setName(e.target.value);
                            }}
                            type="text"
                        />
                    </Col>
                </Row>
                <Row gutter={16} style={{ marginTop: "15px" }}>
                    <Col span={12}>
                        <span>Thời gian bắt đầu:</span> <br />
                        <DatePicker
                            value={startTime}
                            onChange={(date) => {
                                setStartTime(date);
                            }}
                        />
                    </Col>
                    <Col span={12}>
                        <span>Thời gian kết thúc:</span> <br />
                        <DatePicker
                            value={endTime}
                            onChange={(date) => {
                                setEndTime(date);
                            }}
                        />
                    </Col>
                </Row>
                <Row gutter={16} style={{ marginTop: "15px" }}>
                    <Col span={12}>
                        <span>Cấp độ:</span> <br />
                        <Select
                            value={level}
                            onChange={(value) => {
                                setLevel(value);
                            }}
                        >
                            <Option value="easy">Dễ</Option>
                            <Option value="medium">Trung bình</Option>
                            <Option value="hard">Khó</Option>
                        </Select>
                    </Col>
                    <Col span={12}>
                        <span>Semester ID:</span> <br />
                        <Input
                            value={semesterId}
                            onChange={(e) => {
                                setSemesterId(e.target.value);
                            }}
                            type="text"
                        />
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
                        onClick={update}
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
    );
}

export default ModalUpdateActivity;