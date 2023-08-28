import "./styleModalUpdateActivity.css";
import { Modal, Row, Col, Input, Button, DatePicker, Select } from "antd";
import { useEffect, useState } from "react";
import { useAppDispatch } from "../../../../app/hook";
import { toast } from "react-toastify";
import { ActivityManagementAPI } from "../../../../api/admin/activity-management/activityManagement.api";
import { UpdateActivityManagement } from "../../../../app/admin/activity-management/activityManagementSlice.reducer";
import { Option } from "antd/es/mentions";

const ModalUpdateActivity = ({ visible, onCancel, listSemester }) => {
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
            if(listSemester.length > 0) {
                setSemesterId(listSemester[0].id)
            }
            return () => {
                setName("");
                setStartTime("");
                setEndTime("");
                setLevel("0");
                setSemesterId("");
                setErrorName("");
                setErrorStartTime("");
                setErrorEndTime("");
                setErrorLevel("");
                setErrorSemesterId("");
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
        if (level === "") {
            setErrorLevel("Hãy chọn cấp độ");
            check++;
        } else {
            setErrorLevel("");
        }
        const semesterNameItem = listSemester.find((item) => item.id === semesterId)
        if (check === 0) {
            let obj = {
                id: visible.id,
                name: name,
                startTime: startTime,
                endTime: endTime,
                level: level,
                semesterId: semesterId,
            };
            ActivityManagementAPI.update(obj, visible.id).then(
                (response) => {
                    toast.success("Cập nhật thành công!");
                    let objUpdate = {...response.data.data, nameSemester:semesterNameItem.name, levelText: level}
                    dispatch(UpdateActivityManagement(objUpdate));
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
                <span style={{ fontSize: "18px" }}>Sửa hoạt động</span>
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
                        <span className="error">{errorName}</span>
                    </Col>
                </Row>
                <Row gutter={16} style={{ marginTop: "15px" }}>
                    <Col span={12}>
                        <span>Thời gian bắt đầu:</span> <br />
                        <DatePicker style={{width: "100%"}}
                            value={startTime}
                            onChange={(date) => {
                                setStartTime(date);
                            }}
                        />
                        <span className="error">{errorStartTime}</span>
                    </Col>
                    <Col span={12}>
                        <span>Thời gian kết thúc:</span> <br />
                        <DatePicker style={{width: "100%"}}
                            value={endTime}
                            onChange={(date) => {
                                setEndTime(date);
                            }}
                        />
                        <span className="error">{errorEndTime}</span>
                    </Col>
                </Row>
                <Row gutter={16} style={{ marginTop: "15px" }}>
                    <Col span={24}>
                        <span>Cấp độ:</span> <br />
                        <Select style={{ width: "100%" }}
                            value={level}
                            onChange={(value) => {
                                setLevel(value);
                            }}
                        >
                            <Option value="0">level 1</Option>
                            <Option value="1">level 2</Option>
                            <Option value="2">level 3</Option>
                        </Select>
                        <span className="error">{errorLevel}</span>
                    </Col>
                    </Row>
                    <Row gutter={16} style={{ marginTop: "15px", marginBottom: "15px" }}>
                    <Col span={24}>
                    <span>Tên học kỳ:</span> <br />
                        <Select
                            value={semesterId}
                            onChange={(value) => {
                                setSemesterId(value);
                            }}
                            style={{ marginTop: "6px",width:"100%" }}
                        >
                            {listSemester.map((value) => {
                                return (
                                    <Option value={value.id} key={value.id}>
                                        {value.name}
                                    </Option>
                                );
                            })}
                        </Select>
                        <span className="error">{errorSemesterId}</span>
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
                        Cập nhật
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