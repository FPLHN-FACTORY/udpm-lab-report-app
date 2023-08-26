import React from "react";
import "./FloatingDiv.css";

// class FloatingDiv extends React.Component {
const FloatingDiv = ({ items }) => {
  return <div className="floating-div">{items}</div>;
};

export default FloatingDiv;
