import "./styletablePoint.css";
import { useAppDispatch, useAppSelector } from "../../../../../app/hook";
import { Button, Empty, Input, Table, Tag, message } from "antd";
import {
  GetPoint,
  SetPoint,
} from "../../../../../app/teacher/point/tePointSlice.reduce";
import { useState } from "react";
import { SearchOutlined } from "@ant-design/icons";

const TablePoint = () => {
  const [phase1, setPhase1] = useState(0);
  const [phase2, setPhase2] = useState(0);
  const [final, setFinal] = useState(0);
  const dispatch = useAppDispatch();
  const dataSource = useAppSelector(GetPoint);
  const handlePoint = (phase1, phase2, record) => {
    const dataNew = dataSource.map((item1) => {
      if (
        item1.idClass === record.idClass &&
        item1.idStudent === record.idStudent
      ) {
        if (phase1 === "") {
          return {
            ...item1,
            checkPointPhase1: 0.0,
            checkPointPhase2: parseFloat(phase2),
          };
        }
        if (phase2 === "") {
          return {
            ...item1,
            checkPointPhase1: parseFloat(phase1),
            checkPointPhase2: 0.0,
          };
        }
        if (phase1 === parseFloat(phase1)) {
          return {
            ...item1,
            checkPointPhase1: parseFloat(phase1),
            checkPointPhase2: parseFloat(phase2),
          };
        }

        if (phase2 === parseFloat(phase2)) {
          return {
            ...item1,
            checkPointPhase1: parseFloat(phase1),
            checkPointPhase2: parseFloat(phase2),
          };
        }
        return {
          ...item1,
          checkPointPhase1: parseFloat(phase1),
          checkPointPhase2: parseFloat(phase2),
        };
      }
      return item1;
    });
    dispatch(SetPoint(dataNew));
  };
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
    },
    {
      title: "Nhóm",
      dataIndex: "nameTeam",
      key: "nameTeam",
      render: (text, record) => {
        if (text === "") {
          return <Tag color="processing">Chưa vào nhóm</Tag>;
        } else {
          return <span>{text}</span>;
        }
      },
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: 90 }}>
            Đặt lại
          </Button>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) =>
        record.nameTeam.toLowerCase().includes(value.toLowerCase()),
      sorter: (a, b) => a.nameTeam.localeCompare(b.nameTeam),
    },
    {
      title: "Tên sinh viên",
      dataIndex: "nameStudent",
      key: "nameStudent",
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: 90 }}>
            Đặt lại
          </Button>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) =>
        record.nameStudent.toLowerCase().includes(value.toLowerCase()),
      sorter: (a, b) => a.nameStudent.localeCompare(b.nameStudent),
    },
    {
      title: "Email",
      dataIndex: "emailStudent",
      key: "emailStudent",
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: 90 }}>
            Đặt lại
          </Button>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) =>
        record.emailStudent.toLowerCase().includes(value.toLowerCase()),
      sorter: (a, b) => a.emailStudent.localeCompare(b.emailStudent),
    },
    {
      title: "Điểm giai đoạn 1",
      dataIndex: "checkPointPhase1",
      key: "checkPointPhase1",
      render: (text, record) => {
        if (text == null || text === "") {
          text = 0;
        }
        return (
          <Input
            placeholder="Nhập điểm"
            value={text}
            min={0.0}
            max={10.0}
            step={0.1}
            defaultValue={0.0}
            onChange={(e) => {
              const newValue = parseFloat(e.target.value);
              if ((newValue >= 0 && newValue <= 10) || e.target.value === "") {
                setPhase1(e.target.value);
                handlePoint(e.target.value, record.checkPointPhase2, record);
              } else {
                message.warning(
                  "Điểm phải là một số dương lớn hơn 0 và nhỏ hơn 10 !"
                );
              }
            }}
            readOnly
            onDoubleClick={(e) => {
              e.target.readOnly = false;
              e.target.select();
            }}
            onBlur={(e) => {
              e.target.readOnly = true;
            }}
            type="number"
          />
        );
      },
      sorter: (a, b) => a.checkPointPhase1 - b.checkPointPhase1,
    },
    {
      title: "Điểm giai đoạn 2",
      dataIndex: "checkPointPhase2",
      key: "checkPointPhase2",
      render: (text, record) => {
        if (text == null || text === "") {
          text = 0;
        }
        return (
          <Input
            placeholder="Nhập điểm"
            value={text}
            min={0.0}
            max={10.0}
            step={0.1}
            defaultValue={0.0}
            onChange={(e) => {
              const newValue = parseFloat(e.target.value);
              if ((newValue >= 0 && newValue <= 10) || e.target.value === "") {
                setPhase2(e.target.value);
                handlePoint(record.checkPointPhase1, e.target.value, record);
              } else {
                message.warning(
                  "Điểm phải là một số dương lớn hơn 0 và nhỏ hơn 10 !"
                );
              }
            }}
            readOnly
            onDoubleClick={(e) => {
              e.target.readOnly = false;
              e.target.select();
            }}
            onBlur={(e) => {
              e.target.readOnly = true;
            }}
            type="number"
          />
        );
      },
      sorter: (a, b) => a.checkPointPhase2 - b.checkPointPhase2,
    },
    {
      title: "Điểm final",
      dataIndex: "finalPoint",
      key: "finalPoint",
      render: (text, record) => {
        if (
          (record.checkPointPhase1 !== "" && record.checkPointPhase2 !== "") ||
          (record.checkPointPhase1 !== null && record.checkPointPhase2 !== null)
        ) {
          return (
            <div style={{ textAlign: "center" }}>
              {parseFloat(
                (parseFloat(record.checkPointPhase1) +
                  parseFloat(record.checkPointPhase2)) /
                  2
              ).toFixed(2)}
            </div>
          );
        } else {
          return <div tyle={{ textAlign: "center" }}>0</div>;
        }
      },
    },
    {
      title: "Trạng thái điểm",
      dataIndex: "statusPointCustome",
      key: "statusPointCustome",
      render: (text, record) => {
        let ratePoint = parseFloat(
          (parseFloat(record.checkPointPhase1) +
            parseFloat(record.checkPointPhase2)) /
            2
        );
        return (
          <div style={{ textAlign: "center" }}>
            {ratePoint >= record.pointMin ? (
              <Tag
                color="success"
                style={{ width: "60px", textAlign: "center" }}
              >
                Đạt
              </Tag>
            ) : (
              <Tag color="error" style={{ width: "60px", textAlign: "center" }}>
                Trượt
              </Tag>
            )}
          </div>
        );
      },
    },
    {
      title: "Trạng thái điểm danh",
      dataIndex: "statusAttendedCustome",
      key: "statusAttendedCustome",
      render: (text, record) => {
        let rateAttended = parseFloat(
          (parseFloat(record.numberOfSessionAttended) /
            parseFloat(record.numberOfSession)) *
            100
        );
        return (
          <div style={{ textAlign: "center" }}>
            {rateAttended >= record.maximumNumberOfBreaks ? (
              <Tag
                color="success"
                style={{ width: "60px", textAlign: "center" }}
              >
                Đạt
              </Tag>
            ) : (
              <Tag color="error" style={{ width: "60px", textAlign: "center" }}>
                Trượt
              </Tag>
            )}
          </div>
        );
      },
    },
    {
      title: "Tình trạng",
      dataIndex: "statusAll",
      key: "statusAll",
      render: (text, record) => {
        let rateAttended = parseFloat(
          (parseFloat(record.numberOfSessionAttended) /
            parseFloat(record.numberOfSession)) *
            100
        );
        let ratePoint = parseFloat(
          (parseFloat(record.checkPointPhase1) +
            parseFloat(record.checkPointPhase2)) /
            2
        );
        return (
          <div style={{ textAlign: "center" }}>
            {rateAttended >= record.maximumNumberOfBreaks &&
            ratePoint >= record.pointMin ? (
              <Tag
                color="success"
                style={{ width: "60px", textAlign: "center" }}
              >
                Đạt
              </Tag>
            ) : (
              <Tag color="error" style={{ width: "60px", textAlign: "center" }}>
                Trượt
              </Tag>
            )}
          </div>
        );
      },
    },
  ];
  return (
    <>
      {dataSource.length > 0 ? (
        <div className="table-teacher">
          <Table
            columns={columns}
            dataSource={dataSource}
            rowKey="idStudent"
            pagination={false}
          />
        </div>
      ) : (
        <Empty
          imageStyle={{ height: 60 }}
          description={<span>Không có dữ liệu</span>}
        />
      )}
    </>
  );
};
export default TablePoint;
