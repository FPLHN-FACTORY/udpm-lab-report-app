import { Button, Input, Table } from "antd";
import { useAppDispatch, useAppSelector } from "../../../../../app/hook";
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
      render: (text, record, index) => <span>{index + 1}</span>,
    },
    {
      title: "Tên sinh viên",
      dataIndex: "name",
      key: "name",
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
        record.name.toLowerCase().includes(value.toLowerCase()),
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
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
        record.email.toLowerCase().includes(value.toLowerCase()),
      sorter: (a, b) => a.email.localeCompare(b.email),
    },
    {
      title: "Điểm giai đoạn 1",
      dataIndex: "checkPointPhase1",
      key: "checkPointPhase1",
      render: (text, record) => {
        return (
          <>
            {" "}
            <Input
              placeholder="Nhập điểm"
              value={text}
              defaultValue={0}
              // readOnly
              // onDoubleClick={(e) => {
              //   e.target.readOnly = false;
              //   e.target.select();
              // }}
              // onBlur={(e) => {
              //   e.target.readOnly = true;
              // }}

              onChange={(e) => {
                setPhase1(e.target.value);
                handlePoint(e.target.value, record.checkPointPhase2, record);
              }}
              min={0}
              max={10}
              step={0.01}
              type="number"
              pattern="^[0-9]+(\.[0-9]{1,2})?$"
            />
          </>
        );
      },
      sorter: (a, b) => a.checkPointPhase1 - b.checkPointPhase1,
    },
    {
      title: "Điểm giai đoạn 2",
      dataIndex: "checkPointPhase2",
      key: "checkPointPhase2",
      render: (text, record) => {
        return (
          <>
            <Input
              placeholder="Nhập điểm"
              value={text}
              defaultValue={0}
              onChange={(e) => {
                setPhase2(e.target.value);
                handlePoint(record.checkPointPhase1, e.target.value, record);
              }}
              min={0}
              max={10}
              step={0.01}
              type="number"
              pattern="^[0-9]+(\.[0-9]{1,2})?$"
            />
          </>
        );
      },
      sorter: (a, b) => a.checkPointPhase2 - b.checkPointPhase2,
    },
    {
      title: "Điểm final",
      dataIndex: "finalPoint",
      key: "finalPoint",
      render: (text, record) => {
        return (
          <span>
            {parseFloat(
              (parseFloat(record.checkPointPhase1) +
                parseFloat(record.checkPointPhase2)) /
                2
            ).toLocaleString("de-DE")}
          </span>
        );
      },
      sorter: (a, b) => a.finalPoint - b.finalPoint,
    },
  ];
  return (
    <>
      {dataSource.length > 0 ? (
        <Table
          rowKey="id"
          columns={columns}
          dataSource={dataSource}
          pagination={false}
        />
      ) : (
        <p
          style={{
            textAlign: "center",
            marginTop: "100px",
            fontSize: "15px",
          }}
        >
          Lớp học chưa có học sinh nào
        </p>
      )}
    </>
  );
};
export default TablePoint;
